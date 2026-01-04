package com.personal.marketnote.product.port.in.command;

public record AddCartProductCommand(
        Long userId,
        Long productId,
        Long pricePolicyId,
        Short quantity
) {

    public static AddCartProductCommand of(
            Long userId, Long productId, Long pricePolicyId, Short quantity
    ) {
        return new AddCartProductCommand(userId, productId, pricePolicyId, quantity);
    }
}
