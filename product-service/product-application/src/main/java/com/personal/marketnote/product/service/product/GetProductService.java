package com.personal.marketnote.product.service.product;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.application.file.port.in.result.GetFilesResult;
import com.personal.marketnote.common.domain.exception.illegalargument.numberformat.ParsingIntegerException;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.domain.option.ProductOption;
import com.personal.marketnote.product.domain.option.ProductOptionCategory;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.domain.product.ProductSearchTarget;
import com.personal.marketnote.product.domain.product.ProductSortProperty;
import com.personal.marketnote.product.exception.ProductNotFoundException;
import com.personal.marketnote.product.port.in.result.option.SelectableProductOptionCategoryItemResult;
import com.personal.marketnote.product.port.in.result.product.GetProductInfoResult;
import com.personal.marketnote.product.port.in.result.product.GetProductInfoWithOptionsResult;
import com.personal.marketnote.product.port.in.result.product.GetProductsResult;
import com.personal.marketnote.product.port.in.result.product.ProductItemResult;
import com.personal.marketnote.product.port.in.usecase.product.GetProductUseCase;
import com.personal.marketnote.product.port.out.file.FindProductImagesPort;
import com.personal.marketnote.product.port.out.inventory.FindCacheStockPort;
import com.personal.marketnote.product.port.out.inventory.FindStockPort;
import com.personal.marketnote.product.port.out.inventory.SaveCacheStockPort;
import com.personal.marketnote.product.port.out.pricepolicy.FindPricePoliciesPort;
import com.personal.marketnote.product.port.out.pricepolicy.FindPricePolicyPort;
import com.personal.marketnote.product.port.out.product.FindProductPort;
import com.personal.marketnote.product.port.out.productoption.FindProductOptionCategoryPort;
import com.personal.marketnote.product.port.out.result.GetInventoryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.personal.marketnote.common.domain.file.FileSort.*;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetProductService implements GetProductUseCase {
    private final FindProductPort findProductPort;
    private final FindProductOptionCategoryPort findProductOptionCategoryPort;
    private final FindPricePolicyPort findPricePolicyPort;
    private final FindPricePoliciesPort findPricePoliciesPort;
    private final FindProductImagesPort findProductImagesPort;
    private final FindCacheStockPort findCacheStockPort;
    private final SaveCacheStockPort saveCacheStockPort;
    private final FindStockPort findStockPort;

    @Override
    public GetProductInfoWithOptionsResult getProductInfo(Long id, List<Long> selectedOptionIds) {
        // 상단 대표 이미지 목록, 본문 이미지 목록 조회 시작
        CompletableFuture<GetFilesResult> representativeFuture
                = CompletableFuture.supplyAsync(
                () -> findProductImagesPort.findImagesByProductIdAndSort(
                        id, PRODUCT_REPRESENTATIVE_IMAGE
                ).orElse(null)
        );
        CompletableFuture<GetFilesResult> contentFuture
                = CompletableFuture.supplyAsync(
                () -> findProductImagesPort.findImagesByProductIdAndSort(
                        id, PRODUCT_CONTENT_IMAGE
                ).orElse(null)
        );

        // 상품 조회 (병렬 진행)
        Product product = getProduct(id);
        List<ProductOptionCategory> categories
                = findProductOptionCategoryPort.findActiveWithOptionsByProductId(product.getId());

        List<PricePolicy> pricePolicies
                = findPricePoliciesPort.findByProductId(product.getId());
        PricePolicy defaultPricePolicy = pricePolicies.stream()
                .filter(policy -> !policy.hasOptions())
                .findFirst()
                .orElse(null);
        PricePolicy selectedPricePolicy = defaultPricePolicy;

        if (
                product.isFindAllOptionsYn()
                        && FormatValidator.hasValue(selectedOptionIds)
                        && FormatValidator.hasValue(categories)
        ) {
            selectedPricePolicy = pricePolicies.stream()
                    .filter(policy -> FormatValidator.equals(policy.getOptionIds(), selectedOptionIds))
                    .findFirst()
                    .orElse(defaultPricePolicy);
        }

        // 상품 재고 수량 조회
        int stock = -1;
        Long pricePolicyId = selectedPricePolicy.getId();
        try {
            stock = findCacheStockPort.findByPricePolicyId(pricePolicyId);
        } catch (ParsingIntegerException pie) {
            // 현재 재고 수량이 Cache Memory에 저장돼 있지 않으므로 커머스 서비스 요청을 통해 조회
            Set<GetInventoryResult> inventories
                    = findStockPort.findByPricePolicyIds(List.of(pricePolicyId));

            Iterator<GetInventoryResult> iterator = inventories.iterator();
            if (iterator.hasNext()) {
                stock = iterator.next().stock();

                // Cache Memory에 재고 수량 저장
                saveCacheStockPort.save(pricePolicyId, stock);
            }
        }

        GetProductInfoResult productInfo = GetProductInfoResult.from(product, selectedPricePolicy, stock);

        Set<String> selectedFlags = new HashSet<>();
        if (FormatValidator.hasValue(categories) && FormatValidator.hasValue(selectedOptionIds)) {
            Set<Long> selectedIds = new HashSet<>(selectedOptionIds);
            for (ProductOptionCategory category : categories) {
                for (ProductOption option : category.getOptions()) {
                    if (selectedIds.contains(option.getId())) {
                        selectedFlags.add(option.getContent());
                    }
                }
            }
        }

        List<SelectableProductOptionCategoryItemResult> selectableCategories
                = categories.stream()
                .map(c -> SelectableProductOptionCategoryItemResult.from(c, selectedFlags))
                .toList();

        // 이미지 결과 대기
        GetFilesResult representativeImages = representativeFuture.join();
        GetFilesResult contentImages = contentFuture.join();

        return GetProductInfoWithOptionsResult.of(
                productInfo, selectableCategories, representativeImages, contentImages, pricePolicies
        );
    }

    @Override
    public Product getProduct(Long id) {
        return findProductPort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public GetProductsResult getProducts(
            Long categoryId,
            Long cursor,
            int pageSize,
            Sort.Direction sortDirection,
            ProductSortProperty sortProperty,
            ProductSearchTarget searchTarget,
            String searchKeyword
    ) {
        boolean isFirstPage = !FormatValidator.hasValue(cursor);

        Pageable pageable = PageRequest.of(
                0, pageSize + 1, Sort.by(sortDirection, sortProperty.getAlternativeKey())
        );

        boolean isCategorized = FormatValidator.hasValue(categoryId);

        // 여기부터: 상품이 아니라 pricePolicy 기준으로 페이지 조회
        List<PricePolicy> pricePolicies = isCategorized
                ? findPricePoliciesPort.findActivePageByCategoryId(
                categoryId,
                cursor,
                pageable,
                sortProperty,
                searchTarget,
                searchKeyword
        )
                : findPricePoliciesPort.findActivePage(
                cursor,
                pageable,
                sortProperty,
                searchTarget,
                searchKeyword
        );

        // 무한 스크롤 페이지 설정
        boolean hasNext = pricePolicies.size() > pageSize;
        List<PricePolicy> pagePolicies = hasNext
                ? pricePolicies.subList(0, pageSize)
                : pricePolicies;

        Long nextCursor = null;
        if (FormatValidator.hasValue(pagePolicies)) {
            // nextCursor = 마지막 pricePolicy의 id
            nextCursor = pagePolicies.get(pagePolicies.size() - 1).getId();
        }

        // pricePolicy 한 건을 화면에 뿌릴 ProductItemResult 한 건으로 매핑
        List<ProductItemResult> pageItems = pagePolicies.stream()
                .map(this::toProductItem)
                .toList();

        // 상품 카탈로그 이미지 병렬 조회
        Map<Long, GetFilesResult> productIdToImages = new ConcurrentHashMap<>();
        List<CompletableFuture<Void>> futures = pageItems.stream()
                .map(
                        item -> CompletableFuture.runAsync(
                                () -> findProductImagesPort.findImagesByProductIdAndSort(
                                                item.getId(), PRODUCT_CATALOG_IMAGE
                                        )
                                        .ifPresent(result -> productIdToImages.put(item.getId(), result))
                        )
                )
                .toList();
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        List<ProductItemResult> pageItemsWithImage = pageItems.stream()
                .map(item -> ProductItemResult.from(
                                item,
                                FormatValidator.hasValue(productIdToImages.get(item.getId()))
                                        ? productIdToImages.get(item.getId())
                                        .images()
                                        .getFirst()
                                        : null
                        )
                )
                .collect(Collectors.toList());

        Long totalElements = null;
        if (isFirstPage) {
            totalElements = computeTotalElements(isCategorized, categoryId, searchTarget, searchKeyword);
        }

        // 상품 재고 수량 조회
        List<Integer> stocks = findCacheStockPort.findByPricePolicyIds(
                pageItemsWithImage.stream()
                        .map(ProductItemResult::getPricePolicyId)
                        .toList()
        );

        List<Long> pricePolicyIdsWithoutStocks = new ArrayList<>();
        for (int i = 0; i < pageItemsWithImage.size(); i++) {
            Integer stock = stocks.get(i);
            ProductItemResult item = pageItemsWithImage.get(i);
            if (!FormatValidator.hasValue(stock)) {
                pricePolicyIdsWithoutStocks.add(item.getPricePolicyId());
                continue;
            }

            item.addStock(stock);
        }

        if (FormatValidator.hasValue(pricePolicyIdsWithoutStocks)) {
            // Cache Memory에 저장돼 있지 않은 재고 수량 목록은 커머스 서비스 요청을 통해 조회
            Set<GetInventoryResult> inventories
                    = findStockPort.findByPricePolicyIds(pricePolicyIdsWithoutStocks);

            for (GetInventoryResult inventory : inventories) {
                if (!FormatValidator.hasValue(inventory)) {
                    continue;
                }

                Long pricePolicyId = inventory.pricePolicyId();
                Integer stock = inventory.stock();

                pageItemsWithImage.stream()
                        .filter(item -> FormatValidator.equals(item.getPricePolicyId(), pricePolicyId))
                        .findFirst()
                        .ifPresent(item -> item.addStock(stock));

                // Cache Memory에 재고 수량 저장
                saveCacheStockPort.save(pricePolicyId, stock);
            }
        }

        return GetProductsResult.from(hasNext, nextCursor, totalElements, pageItemsWithImage);
    }

    private ProductItemResult toProductItem(PricePolicy pricePolicy) {
        // pricePolicy -> product 조회
        Product product = getProduct(pricePolicy.getProductId());

        // 옵션을 모두 개별 상품으로 노출하지 않는 경우: 옵션 없이 단일 상품
        if (!product.isFindAllOptionsYn()) {
            return ProductItemResult.from(product, pricePolicy);
        }

        List<Long> optionIds = pricePolicy.getOptionIds();
        if (!FormatValidator.hasValue(optionIds)) {
            return ProductItemResult.from(product, pricePolicy);
        }

        // 해당 상품의 옵션 카테고리 + 옵션 전체 조회
        List<ProductOptionCategory> categories =
                findProductOptionCategoryPort.findActiveWithOptionsByProductId(product.getId());

        if (!FormatValidator.hasValue(categories)) {
            return ProductItemResult.from(product, pricePolicy);
        }

        Map<Long, ProductOption> optionMap = categories.stream()
                .flatMap(c -> c.getOptions().stream())
                .collect(Collectors.toMap(ProductOption::getId, o -> o, (o1, o2) -> o1));

        List<ProductOption> selectedOptions = optionIds.stream()
                .map(optionMap::get)
                .filter(Objects::nonNull)
                .toList();

        return ProductItemResult.from(product, selectedOptions, pricePolicy);
    }

    private Long computeTotalElements(
            boolean isCategorized, Long categoryId, ProductSearchTarget searchTarget, String searchKeyword
    ) {
        if (isCategorized) {
            return findProductPort.countActiveByCategoryId(categoryId, searchTarget, searchKeyword);
        }

        return findProductPort.countActive(searchTarget, searchKeyword);
    }

    private List<ProductItemResult> expandProducts(List<Product> products) {
        List<ProductItemResult> results = new ArrayList<>();
        for (Product product : products) {
            if (!product.isFindAllOptionsYn()) {
                results.add(ProductItemResult.from(product));
                continue;
            }

            List<ProductOptionCategory> categories
                    = findProductOptionCategoryPort.findActiveWithOptionsByProductId(product.getId());

            if (
                    !FormatValidator.hasValue(categories)
                            || categories.stream().anyMatch(c -> !FormatValidator.hasValue(c.getOptions()))
            ) {
                results.add(ProductItemResult.from(product));
                continue;
            }

            List<List<ProductOption>> optionGroups = categories.stream()
                    .sorted(Comparator.comparing(ProductOptionCategory::getOrderNum))
                    .map(ProductOptionCategory::getOptions)
                    .toList();

            // 선택된 옵션 조합 추출
            for (List<ProductOption> selectedOptions : cartesianProduct(optionGroups)) {
                List<Long> optionIds = selectedOptions.stream().map(ProductOption::getId).toList();
                PricePolicy pricePolicy
                        = findPricePolicyPort.findByProductAndOptionIds(product.getId(), optionIds)
                        .orElse(product.getDefaultPricePolicy());

                results.add(
                        ProductItemResult.from(product, selectedOptions, pricePolicy)
                );
            }
        }

        return results;
    }

    private List<List<ProductOption>> cartesianProduct(List<List<ProductOption>> productOptionCombos) {
        List<List<ProductOption>> result = new ArrayList<>();
        if (!FormatValidator.hasValue(productOptionCombos)) {
            return result;
        }

        backtrackCartesian(productOptionCombos, 0, new ArrayList<>(), result);

        return result;
    }

    private void backtrackCartesian(
            List<List<ProductOption>> productOptionCombos,
            int depth,
            List<ProductOption> path,
            List<List<ProductOption>> result
    ) {
        if (depth == productOptionCombos.size()) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (ProductOption opt : productOptionCombos.get(depth)) {
            path.add(opt);
            backtrackCartesian(productOptionCombos, depth + 1, path, result);
            path.removeLast();
        }
    }
}
