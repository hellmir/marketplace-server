package com.personal.marketnote.product.port.in.command;

public record RegisterProductCommand(
        Long sellerId,
        String name,
        String brandName,
        String detail,
        Long price,
        Long accumulatedPoint
) {
    public static RegisterProductCommand of(
            Long sellerId, String name, String brandName, String detail, Long price, Long accumulatedPoint
    ) {
        return new RegisterProductCommand(sellerId, name, brandName, detail, price, accumulatedPoint);
    }
}


