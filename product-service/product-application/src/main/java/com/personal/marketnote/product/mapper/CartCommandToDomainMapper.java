package com.personal.marketnote.product.mapper;

import com.personal.marketnote.product.domain.cart.CartProduct;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import com.personal.marketnote.product.port.in.command.AddCartProductCommand;

public class CartCommandToDomainMapper {
    public static CartProduct mapToDomain(
            AddCartProductCommand addCartProductCommand, PricePolicy pricePolicy
    ) {
        return CartProduct.of(
                addCartProductCommand.userId(),
                pricePolicy,
                addCartProductCommand.imageUrl(),
                addCartProductCommand.quantity()
        );
    }
}
