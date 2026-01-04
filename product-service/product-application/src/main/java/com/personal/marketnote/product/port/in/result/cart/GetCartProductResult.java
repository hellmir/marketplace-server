package com.personal.marketnote.product.port.in.result.cart;

import com.personal.marketnote.product.domain.cart.CartProduct;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetCartProductResult(
        CartProductItemResult product,
        GetCartProductPricePolicyResult pricePolicy,
        Short quantity
) {
    public static GetCartProductResult from(CartProduct cartProduct) {
        return GetCartProductResult.builder()
                .pricePolicy(GetCartProductPricePolicyResult.from(cartProduct.getPricePolicy()))
                .product(CartProductItemResult.from(cartProduct.getPricePolicy().getProduct(), cartProduct.getImageUrl()))
                .quantity(cartProduct.getQuantity())
                .build();
    }
}
