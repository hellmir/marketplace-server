package com.personal.marketnote.product.port.in.result;

import com.personal.marketnote.product.domain.product.ProductOption;

public record ProductOptionItemResult(
        Long id,
        String content,
        Long price,
        Long accumulatedPoint,
        String status
) {
    public static ProductOptionItemResult from(ProductOption option) {
        return new ProductOptionItemResult(
                option.getId(),
                option.getContent(),
                option.getPrice(),
                option.getAccumulatedPoint(),
                option.getStatus().name()
        );
    }
}


