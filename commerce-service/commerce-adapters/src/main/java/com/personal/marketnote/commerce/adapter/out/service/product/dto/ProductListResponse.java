package com.personal.marketnote.commerce.adapter.out.service.product.dto;

public record ProductListResponse(
        ProductCursorResponse<ProductItemResponse> products
) {
}
