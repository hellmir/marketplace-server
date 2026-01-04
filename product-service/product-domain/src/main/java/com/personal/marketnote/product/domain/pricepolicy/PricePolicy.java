package com.personal.marketnote.product.domain.pricepolicy;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.product.domain.product.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class PricePolicy {
    private Long id;
    private Product product;
    private Long price;
    private Long discountPrice;
    private BigDecimal accumulationRate;
    private Long accumulatedPoint;
    private BigDecimal discountRate;
    private EntityStatus status;

    public static PricePolicy of(
            Product product,
            Long price,
            Long discountPrice,
            BigDecimal accumulationRate,
            Long accumulatedPoint,
            BigDecimal discountRate
    ) {
        return PricePolicy.builder()
                .product(product)
                .price(price)
                .discountPrice(discountPrice)
                .accumulationRate(accumulationRate)
                .accumulatedPoint(accumulatedPoint)
                .discountRate(discountRate)
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static PricePolicy of(
            Long id,
            Product product,
            Long price,
            Long discountPrice,
            BigDecimal accumulationRate,
            Long accumulatedPoint,
            BigDecimal discountRate
    ) {
        return PricePolicy.builder()
                .id(id)
                .product(product)
                .price(price)
                .discountPrice(discountPrice)
                .accumulationRate(accumulationRate)
                .accumulatedPoint(accumulatedPoint)
                .discountRate(discountRate)
                .status(EntityStatus.ACTIVE)
                .build();
    }
}
