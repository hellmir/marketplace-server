package com.personal.marketnote.product.port.in.command;

public record RegisterProductCommand(
        Long sellerId,
        String name,
        String brandName,
        String detail,
        Long price,
        Long discountPrice,
        Long accumulatedPoint,
        boolean isFindAllOptions
) {
    public static RegisterProductCommand of(
            Long sellerId, String name, String brandName, String detail, Long price,
            Long discountPrice, Long accumulatedPoint, boolean isFindAllOptions
    ) {
        return new RegisterProductCommand(
                sellerId, name, brandName, detail, price, discountPrice, accumulatedPoint, isFindAllOptions
        );
    }
}
