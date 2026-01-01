package com.personal.marketnote.product.port.in.command;

public record RegisterPricePolicyCommand(
        Long productId,
        Long price,
        Long discountPrice,
        Long accumulatedPoint
) {
    public static RegisterPricePolicyCommand of(
            Long productId,
            Long price,
            Long discountPrice,
            Long accumulatedPoint
    ) {
        return new RegisterPricePolicyCommand(
                productId, price, discountPrice, accumulatedPoint
        );
    }

    public static RegisterPricePolicyCommand from(Long productId, RegisterProductCommand registerProductCommand) {
        return RegisterPricePolicyCommand.of(
                productId,
                registerProductCommand.price(),
                registerProductCommand.discountPrice(),
                registerProductCommand.accumulatedPoint()
        );
    }
}
