package com.personal.marketnote.product.port.in.result;

import com.personal.marketnote.product.domain.product.ProductOption;

public record SelectableProductOptionItemResult(
        Long id,
        String content,
        Long price,
        Long accumulatedPoint,
        String status,
        boolean isSelected
) {
    public static SelectableProductOptionItemResult from(ProductOption option, boolean selected) {
        return new SelectableProductOptionItemResult(
                option.getId(),
                option.getContent(),
                option.getPrice(),
                option.getAccumulatedPoint(),
                option.getStatus().name(),
                selected
        );
    }
}
