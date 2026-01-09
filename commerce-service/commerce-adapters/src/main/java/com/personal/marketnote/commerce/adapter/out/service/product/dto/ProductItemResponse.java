package com.personal.marketnote.commerce.adapter.out.service.product.dto;

public record ProductItemResponse(
        Long id,
        String name,
        String brandName,
        ProductPricePolicyResponse pricePolicy
) {
}
