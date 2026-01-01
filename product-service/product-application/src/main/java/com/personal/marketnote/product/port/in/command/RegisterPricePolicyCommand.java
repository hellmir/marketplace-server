package com.personal.marketnote.product.port.in.command;

public record RegisterPricePolicyCommand(
        Long productId,
        Long price,
        Long currentPrice,
        Long accumulatedPoint
) {
    public static RegisterPricePolicyCommand of(
            Long productId,
            Long price,
            Long currentPrice,
            Long accumulatedPoint
    ) {
        return new RegisterPricePolicyCommand(
                productId, price, currentPrice, accumulatedPoint
        );
    }
}
