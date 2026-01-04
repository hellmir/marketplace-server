package com.personal.marketnote.product.port.in.result.cart;

import com.personal.marketnote.product.domain.cart.CartProduct;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record GetMyCartProductsResult(
        List<GetCartProductResult> cartProducts
) {
    public static GetMyCartProductsResult from(List<CartProduct> userCartProducts) {
        return GetMyCartProductsResult.builder()
                .cartProducts(userCartProducts.stream()
                        .map(GetCartProductResult::from)
                        .toList()
                )
                .build();
    }
}
