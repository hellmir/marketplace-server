package com.personal.marketnote.product.port.in.command;

public record RegisterProductCommand(
        Long sellerId,
        String name,
        String detail
) {
    public static RegisterProductCommand of(
            Long sellerId, String name, String detail
    ) {
        return new RegisterProductCommand(sellerId, name, detail);
    }
}


