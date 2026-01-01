package com.personal.marketnote.product.service.product;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.domain.product.*;
import com.personal.marketnote.product.exception.ProductNotFoundException;
import com.personal.marketnote.product.port.in.result.GetProductsResult;
import com.personal.marketnote.product.port.in.result.ProductItemResult;
import com.personal.marketnote.product.port.in.usecase.product.GetProductUseCase;
import com.personal.marketnote.product.port.out.product.FindProductPort;
import com.personal.marketnote.product.port.out.productoption.FindProductOptionCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_UNCOMMITTED, readOnly = true)
public class GetProductService implements GetProductUseCase {
    private final FindProductPort findProductPort;
    private final FindProductOptionCategoryPort findProductOptionCategoryPort;

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
                    .sorted(java.util.Comparator.comparing(ProductOptionCategory::getOrderNum))
                    .map(ProductOptionCategory::getOptions)
                    .toList();

            for (List<ProductOption> combo : cartesianProduct(optionGroups)) {
                // 이름 조합: "기본 상품명 (옵션1 / 옵션2 / ...)"
                String comboSuffix = combo.stream()
                        .map(ProductOption::getContent)
                        .reduce((a, b) -> a + " / " + b)
                        .orElse("");
                String variantName = product.getName() + " (" + comboSuffix + ")";

                long optionPriceSum = combo.stream()
                        .map(ProductOption::getPrice)
                        .filter(java.util.Objects::nonNull)
                        .mapToLong(Long::longValue)
                        .sum();
                long optionPointSum = combo.stream()
                        .map(ProductOption::getAccumulatedPoint)
                        .filter(java.util.Objects::nonNull)
                        .mapToLong(Long::longValue)
                        .sum();

                results.add(
                        ProductItemResult.from(
                                product,
                                variantName,
                                product.getPrice() + optionPointSum,
                                product.getDiscountPrice() + optionPriceSum,
                                product.getDiscountRate(),
                                product.getAccumulatedPoint() + optionPointSum
                        )
                );
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
