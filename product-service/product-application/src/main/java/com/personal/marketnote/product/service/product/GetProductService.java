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
        PricePolicy defaultPricePolicy = product.getDefaultPricePolicy();

        List<PricePolicy> pricePolicies
                = findPricePoliciesPort.findByProductId(product.getId())
                .stream()
                .filter(PricePolicy::hasOptions)
                .collect(Collectors.toList());
        pricePolicies.addFirst(defaultPricePolicy);

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

        GetProductInfoResult productInfo = GetProductInfoResult.from(product, selectedPricePolicy);

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

        // 이미지 결과 대기
        GetFilesResult representativeImages = representativeFuture.join();
        GetFilesResult contentImages = contentFuture.join();

        return GetProductInfoWithOptionsResult.of(
                productInfo, selectableCategories, representativeImages, contentImages, pricePolicies, stock
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
        // 내림차순 정렬인 경우 첫 페이지의 cursor 값을 최대 숫자로 설정
        boolean isFirstPage = !FormatValidator.hasValue(cursor);
        if (isFirstPage && sortDirection.isDescending()) {
            cursor = Long.MAX_VALUE;
        }

        Pageable pageable = PageRequest.of(
                0, pageSize + 1, Sort.by(sortDirection, sortProperty.getAlternativeKey())
        );

        boolean isCategorized = FormatValidator.hasValue(categoryId);

        List<Product> products = getProducts(
                isCategorized,
                categoryId,
                cursor,
                pageable,
                sortProperty,
                searchTarget,
                searchKeyword
        );

        // 옵션 조합 확장
        List<ProductItemResult> expandedProductItems = expandProducts(products);

        // 무한 스크롤 페이지 설정
        boolean hasNext = expandedProductItems.size() > pageSize;
        List<ProductItemResult> pageItems = hasNext
                ? expandedProductItems.subList(0, pageSize)
                : expandedProductItems;

        Long nextCursor = null;
        if (FormatValidator.hasValue(pageItems)) {
            nextCursor = pageItems.getLast().pricePolicy().id();
        }

        // 상품 카탈로그 이미지 병렬 조회
        Map<Long, GetFilesResult> productIdToImages = new ConcurrentHashMap<>();
        List<CompletableFuture<Void>> futures = pageItems.stream()
                .map(
                        item -> CompletableFuture.runAsync(() -> {
                            findProductImagesPort.findImagesByProductIdAndSort(item.id(), PRODUCT_CATALOG_IMAGE)
                                    .ifPresent(result -> productIdToImages.put(item.id(), result));
                        })
                )
                .toList();
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        List<ProductItemResult> pageItemsWithImage = pageItems.stream()
                .map(item -> ProductItemResult.from(
                                item,
                                FormatValidator.hasValue(productIdToImages.get(item.id()))
                                        ? productIdToImages.get(item.id())
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

        return GetProductsResult.fromItems(pageItemsWithImage, hasNext, nextCursor, totalElements);
    }

    private List<Product> getProducts(
            boolean isCategorized,
            Long categoryId,
            Long cursor,
            Pageable pageable,
            ProductSortProperty sortProperty,
            ProductSearchTarget searchTarget,
            String searchKeyword
    ) {
        if (isCategorized) {
            return findProductPort.findAllActiveByCategoryId(
                    categoryId,
                    cursor,
                    pageable,
                    sortProperty,
                    searchTarget,
                    searchKeyword
            );
        }

        return findProductPort.findAllActive(
                cursor,
                pageable,
                sortProperty,
                searchTarget,
                searchKeyword
        );
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
            path.remove(path.size() - 1);
        }
    }
}
