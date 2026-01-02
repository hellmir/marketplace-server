package com.personal.marketnote.product.port.in.result;

import com.personal.marketnote.product.domain.product.ProductOptionCategory;

import java.util.List;
import java.util.stream.Collectors;

public record GetProductOptionsResult(
        List<ProductOptionCategoryItemResult> categories
) {
    public static GetProductOptionsResult from(List<ProductOptionCategory> categories) {
        return new GetProductOptionsResult(
                categories.stream()
                        .map(ProductOptionCategoryItemResult::from)
                        .collect(Collectors.toList())
        );
    }
}
