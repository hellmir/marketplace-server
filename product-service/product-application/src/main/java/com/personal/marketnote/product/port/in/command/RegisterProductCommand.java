package com.personal.marketnote.product.port.in.command;

import java.util.List;

public record RegisterProductCommand(
        Long sellerId,
        String name,
        String brandName,
        String detail,
        Long price,
        Long discountPrice,
        Long accumulatedPoint,
        Boolean isFindAllOptions,
        List<String> tags
) {
    public static RegisterProductCommand of(
            Long sellerId, String name, String brandName, String detail, Long price,
            Long discountPrice, Long accumulatedPoint, Boolean isFindAllOptions, List<String> tags
    ) {
        return new RegisterProductCommand(
                sellerId, name, brandName, detail, price, discountPrice, accumulatedPoint, isFindAllOptions, tags
        );
    }
}
