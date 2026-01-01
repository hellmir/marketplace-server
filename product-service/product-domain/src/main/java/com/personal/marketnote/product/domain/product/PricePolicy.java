package com.personal.marketnote.product.domain.product;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
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
    private Long currentPrice;
    private BigDecimal accumulationRate;
    private Long accumulatedPoint;
    private BigDecimal discountRate;
    private EntityStatus status;

    public static PricePolicy of(
            Product product,
            Long price,
            Long currentPrice,
            BigDecimal accumulationRate,
            Long accumulatedPoint,
            BigDecimal discountRate
    ) {
        return PricePolicy.builder()
                .product(product)
                .price(price)
                .currentPrice(currentPrice)
                .accumulationRate(accumulationRate)
                .accumulatedPoint(accumulatedPoint)
                .discountRate(discountRate)
                .status(EntityStatus.ACTIVE)
                .build();
    }
}


