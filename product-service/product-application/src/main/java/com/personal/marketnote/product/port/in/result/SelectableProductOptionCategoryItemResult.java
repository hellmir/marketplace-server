package com.personal.marketnote.product.port.in.result;

import com.personal.marketnote.product.domain.product.ProductOptionCategory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record SelectableProductOptionCategoryItemResult(
        Long id,
        String name,
        Long orderNum,
        String status,
        List<SelectableProductOptionItemResult> options
) {
    public static SelectableProductOptionCategoryItemResult from(
            ProductOptionCategory category,
            Set<String> selectedOptionContents
    ) {
        return new SelectableProductOptionCategoryItemResult(
                category.getId(),
                category.getName(),
                category.getOrderNum(),
                category.getStatus().name(),
                category.getOptions().stream()
                        .map(opt -> SelectableProductOptionItemResult.from(
                                opt,
                                containsIgnoreCase(selectedOptionContents, opt.getContent())
                        ))
                        .collect(Collectors.toList())
        );
    }

    private static boolean containsIgnoreCase(Set<String> set, String value) {
        if (set == null || set.isEmpty() || value == null) {
            return false;
        }
        for (String v : set) {
            if (value.equalsIgnoreCase(v)) {
                return true;
            }
        }
        return false;
    }
}
