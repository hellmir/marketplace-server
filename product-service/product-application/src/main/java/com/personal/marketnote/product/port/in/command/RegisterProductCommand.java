package com.personal.marketnote.product.port.in.command;

public record RegisterProductCommand(
        Long sellerId,
        String name,
        String detail,
        Long orderNumber
) {
}


