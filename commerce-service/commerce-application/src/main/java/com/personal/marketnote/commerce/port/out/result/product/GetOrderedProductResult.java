package com.personal.marketnote.commerce.port.out.result.product;

public record GetOrderedProductResult(
        Long productId,
        String name,
        String brandName
) {
}