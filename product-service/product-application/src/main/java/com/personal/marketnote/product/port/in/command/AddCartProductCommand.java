package com.personal.marketnote.product.port.in.command;

public record AddCartProductCommand(
        Long userId,
        Long pricePolicyId,
        String imageUrl,
        Short quantity
) {

    public static AddCartProductCommand of(
            Long userId, Long pricePolicyId, String imageUrl, Short quantity
    ) {
        return new AddCartProductCommand(userId, pricePolicyId, imageUrl, quantity);
    }
}
