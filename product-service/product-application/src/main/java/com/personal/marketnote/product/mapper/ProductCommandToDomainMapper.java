package com.personal.marketnote.product.mapper;

import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.domain.product.ProductOption;
import com.personal.marketnote.product.domain.product.ProductOptionCategory;
import com.personal.marketnote.product.port.in.command.RegisterProductOptionsCommand;

import java.util.stream.Collectors;

public class ProductCommandToDomainMapper {
    public static ProductOptionCategory mapToDomain(
            Product product, RegisterProductOptionsCommand registerProductOptionsCommand
    ) {
        return ProductOptionCategory.of(
                product,
                registerProductOptionsCommand.categoryName(),
                registerProductOptionsCommand.options()
                        .stream()
                        .map(ProductCommandToDomainMapper::mapToDomain)
                        .collect(Collectors.toList())
        );
    }

    public static ProductOption mapToDomain(RegisterProductOptionsCommand.OptionItem optionItem) {
        return ProductOption.of(
                optionItem.content(),
                optionItem.price(),
                optionItem.accumulatedPoint()
        );
    }
}
