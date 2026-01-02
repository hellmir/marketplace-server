package com.personal.marketnote.product.service.product;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.domain.product.*;
import com.personal.marketnote.product.exception.ProductNotFoundException;
import com.personal.marketnote.product.port.in.result.*;
import com.personal.marketnote.product.port.in.usecase.product.GetProductUseCase;
import com.personal.marketnote.product.port.out.pricepolicy.FindPricePolicyPort;
import com.personal.marketnote.product.port.out.product.FindProductPort;
import com.personal.marketnote.product.port.out.productoption.FindProductOptionCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetProductService implements GetProductUseCase {
    private final FindProductPort findProductPort;
    private final FindProductOptionCategoryPort findProductOptionCategoryPort;
    private final FindPricePolicyPort findPricePolicyPort;

    @Override
    public Product getProduct(Long id) {
        return findProductPort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public GetProductInfoWithOptionsResult getProductInfo(Long id, List<String> optionContents) {
        Product product = getProduct(id);

        Set<String> selectedOptions = FormatValidator.hasValue(optionContents)
                ? new HashSet<>(optionContents)
                : Collections.emptySet();

        List<ProductOptionCategory> categories
                = findProductOptionCategoryPort.findActiveWithOptionsByProductId(product.getId());

        Long adjustedPrice = product.getPrice();
        Long adjustedDiscountPrice = product.getDiscountPrice() != null ? product.getDiscountPrice() : product.getPrice();
        Long adjustedAccumulatedPoint = product.getAccumulatedPoint();
        if (product.isFindAllOptionsYn() && FormatValidator.hasValue(selectedOptions) && FormatValidator.hasValue(categories)) {
            // 지원: 옵션 이름 또는 옵션 ID 문자열 모두 허용
            java.util.Set<Long> numericSelected = new java.util.HashSet<>();
            for (String s : selectedOptions) {
                if (s == null) continue;
                String t = s.trim();
                try {
                    if (!t.isEmpty()) {
                        numericSelected.add(Long.parseLong(t));
                    }
                } catch (NumberFormatException ignore) {
                }
            }
            List<Long> selectedIds = new ArrayList<>();
            for (ProductOptionCategory c : categories) {
                for (ProductOption o : c.getOptions()) {
                    if (selectedOptions.stream().anyMatch(s -> s.equalsIgnoreCase(o.getContent()))
                            || numericSelected.contains(o.getId())) {
                        selectedIds.add(o.getId());
                    }
                }
            }
            if (FormatValidator.hasValue(selectedIds)) {
                var pvOpt = findPricePolicyPort.findByProductAndOptionIds(product.getId(), selectedIds);
                if (pvOpt.isPresent()) {
                    var pv = pvOpt.get();
                    adjustedPrice = pv.getPrice();
                    adjustedDiscountPrice = pv.getDiscountPrice();
                    adjustedAccumulatedPoint = pv.getAccumulatedPoint();
                    if (adjustedDiscountPrice != null && adjustedPrice != null && adjustedDiscountPrice > adjustedPrice) {
                        adjustedDiscountPrice = adjustedPrice;
                    }
                }
            }
        }

        GetProductInfoResult info = GetProductInfoResult.fromAdjusted(
                product, adjustedPrice, adjustedDiscountPrice, adjustedAccumulatedPoint
        );

        // 선택 플래그 표시: 숫자 ID로 받은 경우에도 content를 선택 처리
        java.util.Set<String> selectedFlags = new java.util.HashSet<>(selectedOptions);
        if (FormatValidator.hasValue(categories)) {
            java.util.Set<Long> numericSelectedForFlags = new java.util.HashSet<>();
            for (String s : selectedOptions) {
                if (s == null) continue;
                try {
                    numericSelectedForFlags.add(Long.parseLong(s.trim()));
                } catch (NumberFormatException ignore) {
                }
            }
            if (!numericSelectedForFlags.isEmpty()) {
                for (ProductOptionCategory c : categories) {
                    for (ProductOption o : c.getOptions()) {
                        if (numericSelectedForFlags.contains(o.getId())) {
                            selectedFlags.add(o.getContent());
                        }
                    }
                }
            }
        }
        List<SelectableProductOptionCategoryItemResult> selectableCategories
                = categories.stream()
                .map(c -> SelectableProductOptionCategoryItemResult.from(c, selectedFlags))
                .toList();

        return GetProductInfoWithOptionsResult.of(info, selectableCategories);
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

        Long totalElements = null;
        if (isFirstPage) {
            totalElements = computeTotalElements(isCategorized, categoryId, searchTarget, searchKeyword);
        }

        return GetProductsResult.fromItems(pageItems, hasNext, nextCursor, totalElements);
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
                var pvOpt = findPricePolicyPort.findByProductAndOptionIds(product.getId(), optionIds);
                Long variantPrice = product.getPrice();
                Long variantDiscountPrice = product.getDiscountPrice();
                java.math.BigDecimal variantDiscountRate = product.getDiscountRate();
                Long variantAccumulatedPoint = product.getAccumulatedPoint();
                if (pvOpt.isPresent()) {
                    var pv = pvOpt.get();
                    variantPrice = pv.getPrice();
                    variantDiscountPrice = pv.getDiscountPrice();
                    variantAccumulatedPoint = pv.getAccumulatedPoint();
                    variantDiscountRate = pv.getDiscountRate();
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
