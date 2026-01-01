package com.personal.marketnote.product.port.in.command;

public record UpdateProductCommand(
        Long productId,
        String name,
        String brandName,
        String detail,
        boolean isFindAllOptions
) {
    public static UpdateProductCommand of(
            Long productId, String name, String brandName, String detail, boolean isFindAllOptions
    ) {
        return new UpdateProductCommand(productId, name, brandName, detail, isFindAllOptions);
    }
}


