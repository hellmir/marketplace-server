package com.personal.marketnote.product.port.in.result.product;

import java.util.List;

public record GetAdminProductsResult(
        Long totalElements,
        Long nextCursor,
        boolean hasNext,
        List<AdminProductItemResult> products
) {
    public static GetAdminProductsResult of(
            Long totalElements,
            Long nextCursor,
            boolean hasNext,
            List<AdminProductItemResult> products
    ) {
        return new GetAdminProductsResult(totalElements, nextCursor, hasNext, products);
    }
}
