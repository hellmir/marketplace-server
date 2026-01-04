package com.personal.marketnote.product.port.in.result.option;

import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.domain.option.ProductOptionCategory;

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
                        .map(option -> SelectableProductOptionItemResult.from(
                                option,
                                containsIgnoreCase(selectedOptionContents, option.getContent())
                        ))
                        .collect(Collectors.toList())
        );
    }

    private static boolean containsIgnoreCase(Set<String> selectedOptionContents, String targetOptionContent) {
        if (FormatValidator.hasValue(selectedOptionContents) && FormatValidator.hasValue(targetOptionContent)) {
            return selectedOptionContents.stream()
                    .anyMatch(selectedOptionContent -> selectedOptionContent.equalsIgnoreCase(targetOptionContent));
        }

        return false;
    }
}
