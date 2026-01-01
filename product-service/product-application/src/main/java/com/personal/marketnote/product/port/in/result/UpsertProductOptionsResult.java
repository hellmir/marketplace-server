package com.personal.marketnote.product.port.in.result;

import com.personal.marketnote.product.domain.product.ProductOption;
import com.personal.marketnote.product.domain.product.ProductOptionCategory;

import java.util.List;
import java.util.stream.Collectors;

public record UpsertProductOptionsResult(
        Long id,
        List<Long> optionIds
) {
    public static UpsertProductOptionsResult from(ProductOptionCategory productOptionCategory) {
        return new UpsertProductOptionsResult(
                productOptionCategory.getId(),
                productOptionCategory.getOptions().stream()
                        .map(ProductOption::getId)
                        .collect(Collectors.toList())
        );
    }
}
