package com.personal.marketnote.product.mapper;

import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.domain.product.ProductOption;
import com.personal.marketnote.product.domain.product.ProductOptionCategory;
import com.personal.marketnote.product.port.in.command.RegisterProductOptionsCommand;

import java.util.stream.Collectors;

public class ProductCommandToDomainMapper {
    public static ProductOptionCategory mapToDomain(
            Product product, RegisterProductOptionsCommand registerProductOptionsCommand) {
        return ProductOptionCategory.of(
                product,
                registerProductOptionsCommand.categoryName(),
                registerProductOptionsCommand.options()
                        .stream()
                        .map(ProductCommandToDomainMapper::mapToDomain)
                        .collect(Collectors.toList()));
    }

    public static ProductOption mapToDomain(RegisterProductOptionsCommand.OptionItem optionItem) {
        return ProductOption.of(
                optionItem.content(),
                optionItem.price(),
                optionItem.accumulatedPoint());
    }

    public static com.personal.marketnote.product.domain.product.PricePolicy mapToDomain(
            Product product, com.personal.marketnote.product.port.in.command.RegisterPricePolicyCommand command) {
        java.math.BigDecimal price = java.math.BigDecimal.valueOf(command.price());
        java.math.BigDecimal currentPrice = java.math.BigDecimal.valueOf(command.currentPrice());
        java.math.BigDecimal hundred = new java.math.BigDecimal("100");

        // discountRate = (price - currentPrice) / price * 100
        java.math.BigDecimal discountRate = price.subtract(currentPrice)
                .divide(price, 3, java.math.RoundingMode.HALF_UP)
                .multiply(hundred)
                .setScale(1, java.math.RoundingMode.HALF_UP);

        // accumulationRate = accumulatedPoint / price * 100
        java.math.BigDecimal accumulationRate = java.math.BigDecimal.valueOf(command.accumulatedPoint())
                .divide(price, 3, java.math.RoundingMode.HALF_UP)
                .multiply(hundred)
                .setScale(1, java.math.RoundingMode.HALF_UP);

        return com.personal.marketnote.product.domain.product.PricePolicy.of(
                product,
                command.price(),
                command.currentPrice(),
                accumulationRate,
                command.accumulatedPoint(),
                discountRate);
    }
}
