package com.personal.marketnote.product.service.product;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.application.file.port.in.result.GetFilesResult;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.domain.product.*;
import com.personal.marketnote.product.exception.ProductNotFoundException;
import com.personal.marketnote.product.port.in.result.*;
import com.personal.marketnote.product.port.in.usecase.product.GetProductUseCase;
import com.personal.marketnote.product.port.out.file.FindProductCatalogImagePort;
import com.personal.marketnote.product.port.out.pricepolicy.FindPricePolicyPort;
import com.personal.marketnote.product.port.out.product.FindProductPort;
import com.personal.marketnote.product.port.out.productoption.FindProductOptionCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetProductService implements GetProductUseCase {
    private final FindProductPort findProductPort;
    private final FindProductOptionCategoryPort findProductOptionCategoryPort;
    private final FindPricePolicyPort findPricePolicyPort;
    private final FindProductCatalogImagePort findProductCatalogImagePort;

    @Override
    public Product getProduct(Long id) {
        return findProductPort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public GetProductInfoWithOptionsResult getProductInfo(Long id, List<Long> selectedOptionIds) {
        // 이미지 두 종류 병렬 조회 시작
        CompletableFuture<GetFilesResult> representativeFuture =
                CompletableFuture.supplyAsync(() ->
                        findProductCatalogImagePort.findImagesByProductIdAndSort(id, "PRODUCT_REPRESENTATIVE_IMAGE").orElse(null)
                );
        CompletableFuture<GetFilesResult> contentFuture =
                CompletableFuture.supplyAsync(() ->
                        findProductCatalogImagePort.findImagesByProductIdAndSort(id, "PRODUCT_CONTENT_IMAGE").orElse(null)
                );

        // 상품 조회 (동시에 진행)
        Product product = getProduct(id);

        List<ProductOptionCategory> categories
                = findProductOptionCategoryPort.findActiveWithOptionsByProductId(product.getId());

        Long adjustedPrice = product.getPrice();
        Long adjustedDiscountPrice = FormatValidator.hasValue(product.getDiscountPrice())
                ? product.getDiscountPrice()
                : product.getPrice();
        Long adjustedAccumulatedPoint = product.getAccumulatedPoint();

        if (
                product.isFindAllOptionsYn()
                        && FormatValidator.hasValue(selectedOptionIds)
                        && FormatValidator.hasValue(categories)
        ) {
            Optional<PricePolicy> pricePolicyOpt
                    = findPricePolicyPort.findByProductAndOptionIds(product.getId(), selectedOptionIds);
            if (pricePolicyOpt.isPresent()) {
                PricePolicy pricePolicy = pricePolicyOpt.get();
                adjustedPrice = pricePolicy.getPrice();
                adjustedDiscountPrice = pricePolicy.getDiscountPrice();
                adjustedAccumulatedPoint = pricePolicy.getAccumulatedPoint();

                if (
                        FormatValidator.hasValue(adjustedDiscountPrice)
                                && FormatValidator.hasValue(adjustedPrice)
                                && adjustedDiscountPrice > adjustedPrice
                ) {
                    adjustedDiscountPrice = adjustedPrice;
                }
            }
        }

        GetProductInfoResult info = GetProductInfoResult.fromAdjusted(
                product, adjustedPrice, adjustedDiscountPrice, adjustedAccumulatedPoint
        );

        Set<String> selectedFlags = new HashSet<>();
        if (FormatValidator.hasValue(categories) && FormatValidator.hasValue(selectedOptionIds)) {
            Set<Long> selectedIdSet = new HashSet<>(selectedOptionIds);
            for (ProductOptionCategory category : categories) {
                for (ProductOption option : category.getOptions()) {
                    if (selectedIdSet.contains(option.getId())) {
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

        return GetProductInfoWithOptionsResult.of(info, selectableCategories, representativeImages, contentImages);
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
                0, pageSize + 1, Sort.by(sortDirection, sortProperty.getCamelCaseValue())
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
            nextCursor = pageItems.get(pageItems.size() - 1).id();
        }

        // 상품 카탈로그 이미지 병렬 조회
        Map<Long, GetFilesResult> productIdToImages = new ConcurrentHashMap<>();
        List<CompletableFuture<Void>> futures = pageItems.stream()
                .map(
                        item -> CompletableFuture.runAsync(() -> {
                            findProductCatalogImagePort.findCatalogImagesByProductId(item.id())
                                    .ifPresent(dto -> productIdToImages.put(item.id(), dto));
                        })
                )
                .toList();
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        List<ProductItemResult> pageItemsWithImage = pageItems.stream()
                .map(item -> ProductItemResult.withCatalogImages(item, productIdToImages.get(item.id())))
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

            for (List<ProductOption> combo : cartesianProduct(optionGroups)) {
                // 이름 조합: "기본 상품명 (옵션1 / 옵션2 / ...)"
                String comboSuffix = combo.stream()
                        .map(ProductOption::getContent)
                        .reduce((a, b) -> a + " / " + b)
                        .orElse("");
                String variantName = product.getName() + " (" + comboSuffix + ")";

                List<Long> optionIds = combo.stream().map(ProductOption::getId).toList();
                Optional<PricePolicy> pricePolicyOpt
                        = findPricePolicyPort.findByProductAndOptionIds(product.getId(), optionIds);
                Long variantPrice = product.getPrice();
                Long variantDiscountPrice = product.getDiscountPrice();
                BigDecimal variantDiscountRate = product.getDiscountRate();
                Long variantAccumulatedPoint = product.getAccumulatedPoint();

                if (pricePolicyOpt.isPresent()) {
                    PricePolicy pricePolicy = pricePolicyOpt.get();
                    variantPrice = pricePolicy.getPrice();
                    variantDiscountPrice = pricePolicy.getDiscountPrice();
                    variantAccumulatedPoint = pricePolicy.getAccumulatedPoint();
                    variantDiscountRate = pricePolicy.getDiscountRate();
                }

                results.add(ProductItemResult.from(
                        product,
                        variantName,
                        variantPrice,
                        variantDiscountPrice,
                        variantDiscountRate,
                        variantAccumulatedPoint
                ));
            }
        }

        return results;
    }

    private List<List<ProductOption>> cartesianProduct(List<List<ProductOption>> lists) {
        List<List<ProductOption>> result = new ArrayList<>();
        if (!FormatValidator.hasValue(lists)) {
            return result;
        }
        backtrackCartesian(lists, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrackCartesian(
            List<List<ProductOption>> lists,
            int depth,
            List<ProductOption> path,
            List<List<ProductOption>> result
    ) {
        if (depth == lists.size()) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (ProductOption opt : lists.get(depth)) {
            path.add(opt);
            backtrackCartesian(lists, depth + 1, path, result);
            path.remove(path.size() - 1);
        }
    }
}
