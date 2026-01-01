package com.personal.marketnote.product.port.in.command;

public record RegisterProductCommand(
        Long sellerId,
        String name,
        String brandName,
        String detail
) {
    public static RegisterProductCommand of(
            Long sellerId, String name, String brandName, String detail
    ) {
        return new RegisterProductCommand(sellerId, name, brandName, detail);
    }
}


