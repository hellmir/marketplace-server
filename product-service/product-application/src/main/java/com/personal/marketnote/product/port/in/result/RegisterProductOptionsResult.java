package com.personal.marketnote.product.port.in.result;

import com.personal.marketnote.product.domain.product.ProductOption;
import com.personal.marketnote.product.domain.product.ProductOptionCategory;

import java.util.List;
import java.util.stream.Collectors;

public record RegisterProductOptionsResult(
        Long id,
        List<Long> optionIds
) {
    public static RegisterProductOptionsResult from(ProductOptionCategory productOptionCategory) {
        return new RegisterProductOptionsResult(
                productOptionCategory.getId(),
                productOptionCategory.getOptions().stream()
                        .map(ProductOption::getId)
                        .collect(Collectors.toList())
        );
    }
}
