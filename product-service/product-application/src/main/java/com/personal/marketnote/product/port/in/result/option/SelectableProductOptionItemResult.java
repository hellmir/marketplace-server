package com.personal.marketnote.product.port.in.result.option;

import com.personal.marketnote.product.domain.option.ProductOption;

public record SelectableProductOptionItemResult(
        Long id,
        String content,
        String status,
        boolean isSelected
) {
    public static SelectableProductOptionItemResult from(ProductOption option, boolean isSelected) {
        return new SelectableProductOptionItemResult(
                option.getId(),
                option.getContent(),
                option.getStatus().name(),
                isSelected
        );
    }
}
