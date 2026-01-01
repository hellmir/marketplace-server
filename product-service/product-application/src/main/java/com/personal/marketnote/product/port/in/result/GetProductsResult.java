package com.personal.marketnote.product.port.in.result;

import com.personal.marketnote.product.domain.product.Product;

import java.util.List;
import java.util.stream.Collectors;

public record GetProductsResult(
        Long totalElements,
        Long nextCursor,
        boolean hasNext,
        List<ProductItemResult> products
) {
    public static GetProductsResult from(
            List<Product> products,
            boolean hasNext,
            Long nextCursor,
            Long totalElements
    ) {
        return new GetProductsResult(
                totalElements,
                nextCursor,
                hasNext,
                products.stream()
                        .map(ProductItemResult::from)
                        .collect(Collectors.toList()));
    }

    public static GetProductsResult fromItems(
            List<ProductItemResult> items,
            boolean hasNext,
            Long nextCursor,
            Long totalElements
    ) {
        return new GetProductsResult(
                totalElements,
                nextCursor,
                hasNext,
                items
        );
    }
}
