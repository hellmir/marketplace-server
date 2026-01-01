package com.personal.marketnote.product.service.product;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.domain.product.ProductSearchTarget;
import com.personal.marketnote.product.domain.product.ProductSortProperty;
import com.personal.marketnote.product.exception.ProductNotFoundException;
import com.personal.marketnote.product.port.in.result.GetProductsResult;
import com.personal.marketnote.product.port.in.usecase.product.GetProductUseCase;
import com.personal.marketnote.product.port.out.product.FindProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_UNCOMMITTED, readOnly = true)
public class GetProductService implements GetProductUseCase {
    private final FindProductPort findProductPort;

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

        boolean hasNext = products.size() > pageSize;
        List<Product> pageItems = hasNext
                ? products.subList(0, pageSize)
                : products;

        Long nextCursor = null;
        if (FormatValidator.hasValue(pageItems)) {
            nextCursor = pageItems.get(pageItems.size() - 1).getId();
        }

        Long totalElements = null;
        if (isFirstPage) {
            totalElements = computeTotalElements(isCategorized, categoryId, searchTarget, searchKeyword);
        }

        return GetProductsResult.from(pageItems, hasNext, nextCursor, totalElements);
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
}
