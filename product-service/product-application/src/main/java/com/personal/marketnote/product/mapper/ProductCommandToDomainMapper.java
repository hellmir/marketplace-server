package com.personal.marketnote.product.mapper;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.product.domain.option.ProductOptionCategory;
import com.personal.marketnote.product.domain.option.ProductOptionCategoryCreateState;
import com.personal.marketnote.product.domain.option.ProductOptionCreateState;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicyCreateState;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.port.in.command.RegisterPricePolicyCommand;
import com.personal.marketnote.product.port.in.command.RegisterProductOptionsCommand;

import java.util.stream.Collectors;

public class ProductCommandToDomainMapper {
    public static ProductOptionCategory mapToDomain(
            Product product, RegisterProductOptionsCommand registerProductOptionsCommand
    ) {
        return ProductOptionCategory.from(
                ProductOptionCategoryCreateState.builder()
                        .product(product)
                        .name(registerProductOptionsCommand.categoryName())
                        .optionStates(
                                registerProductOptionsCommand.options()
                                        .stream()
                                        .map(ProductCommandToDomainMapper::mapToDomain)
                                        .collect(Collectors.toList())
                        )
                        .build()
        );
    }

    public static ProductOptionCreateState mapToDomain(RegisterProductOptionsCommand.OptionItem optionItem) {
        return ProductOptionCreateState.builder()
                .content(optionItem.content())
                .build();
    }

    public static PricePolicy mapToDomain(
            Product product, RegisterPricePolicyCommand command
    ) {
        java.math.BigDecimal price = java.math.BigDecimal.valueOf(command.price());
        java.math.BigDecimal discountPrice = java.math.BigDecimal.valueOf(command.discountPrice());
        java.math.BigDecimal hundred = new java.math.BigDecimal("100");

        // discountRate = (price - discountPrice) / price * 100
        java.math.BigDecimal discountRate = price.subtract(discountPrice)
                .divide(price, 3, java.math.RoundingMode.HALF_UP)
                .multiply(hundred)
                .setScale(1, java.math.RoundingMode.HALF_UP);

        // accumulationRate = accumulatedPoint / discountPrice * 100
        java.math.BigDecimal accumulationRate = java.math.BigDecimal.valueOf(command.accumulatedPoint())
                .divide(discountPrice, 3, java.math.RoundingMode.HALF_UP)
                .multiply(hundred)
                .setScale(1, java.math.RoundingMode.HALF_UP);

        return PricePolicy.from(
                PricePolicyCreateState.builder()
                        .product(product)
                        .price(command.price())
                        .discountPrice(command.discountPrice())
                        .discountRate(discountRate)
                        .accumulatedPoint(command.accumulatedPoint())
                        .accumulationRate(accumulationRate)
                        .status(EntityStatus.ACTIVE)
                        .optionIds(command.optionIds())
                        .build()
        );
    }
}
