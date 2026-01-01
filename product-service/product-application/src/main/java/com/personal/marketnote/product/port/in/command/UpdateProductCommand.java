package com.personal.marketnote.product.port.in.command;

public record UpdateProductCommand(
        Long productId,
        String name,
        String brandName,
        String detail
) {
    public static UpdateProductCommand of(
            Long productId, String name, String brandName, String detail
    ) {
        return new UpdateProductCommand(productId, name, brandName, detail);
    }
}


