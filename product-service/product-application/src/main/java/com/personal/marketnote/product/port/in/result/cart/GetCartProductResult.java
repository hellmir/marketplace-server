package com.personal.marketnote.product.port.in.result.cart;

import com.personal.marketnote.product.domain.cart.CartProduct;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import com.personal.marketnote.product.port.in.result.pricepolicy.GetProductPricePolicyResult;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetCartProductResult(
        CartProductItemResult product,
        GetProductPricePolicyResult pricePolicy,
        Integer stock,
        Short quantity
) {
    public static GetCartProductResult from(CartProduct cartProduct, Integer stock) {
        return GetCartProductResult.builder()
                .pricePolicy(GetProductPricePolicyResult.from(cartProduct.getPricePolicy()))
                .product(CartProductItemResult.from(cartProduct.getPricePolicy().getProduct(), cartProduct.getImageUrl()))
                .stock(stock)
                .quantity(cartProduct.getQuantity())
                .build();
    }

    public static GetCartProductResult of(PricePolicy pricePolicy, String imageUrl, Short quantity, Integer stock) {
        return GetCartProductResult.builder()
                .pricePolicy(GetProductPricePolicyResult.from(pricePolicy))
                .product(CartProductItemResult.from(pricePolicy.getProduct(), imageUrl))
                .quantity(quantity)
                .stock(stock)
                .build();
    }
}
