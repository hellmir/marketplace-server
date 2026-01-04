package com.personal.marketnote.product.adapter.in.client.cart.request;

public record AddCartProductRequest(
        Long productId,
        Long pricePolicyId,
        Short quantity
) {
}


