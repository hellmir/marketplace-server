package com.personal.marketnote.product.port.in.command;

public record RegisterPricePolicyCommand(
        Long productId,
        Long price,
        Long discountPrice,
        Long accumulatedPoint,
        java.util.List<Long> optionIds
) {
    public static RegisterPricePolicyCommand of(
            Long productId,
            Long price,
            Long discountPrice,
            Long accumulatedPoint,
            java.util.List<Long> optionIds
    ) {
        return new RegisterPricePolicyCommand(
                productId, price, discountPrice, accumulatedPoint, optionIds
        );
    }

    public static RegisterPricePolicyCommand from(Long productId, RegisterProductCommand registerProductCommand) {
        return RegisterPricePolicyCommand.of(
                productId,
                registerProductCommand.price(),
                registerProductCommand.discountPrice(),
                registerProductCommand.accumulatedPoint(),
                null
        );
    }
}
