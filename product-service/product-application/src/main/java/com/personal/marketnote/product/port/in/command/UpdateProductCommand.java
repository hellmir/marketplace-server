package com.personal.marketnote.product.port.in.command;

import java.util.List;

public record UpdateProductCommand(
        Long id,
        String name,
        String brandName,
        String detail,
        Boolean isFindAllOptions,
        List<String> tags
) {
    public static UpdateProductCommand of(
            Long id, String name, String brandName, String detail, Boolean isFindAllOptions, List<String> tags
    ) {
        return new UpdateProductCommand(id, name, brandName, detail, isFindAllOptions, tags);
    }
}


