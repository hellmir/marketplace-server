package com.personal.marketnote.product.port.in.result.pricepolicy;

import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import lombok.AccessLevel;
import lombok.Builder;

import java.math.BigDecimal;

@Builder(access = AccessLevel.PRIVATE)
public record GetProductPricePolicyResult(
        Long id,
        Long price,
        Long discountPrice,
        BigDecimal discountRate,
        Long accumulatedPoint
) {
    public static GetProductPricePolicyResult from(PricePolicy pricePolicy) {
        return GetProductPricePolicyResult.builder()
                .id(pricePolicy.getId())
                .price(pricePolicy.getPrice())
                .discountPrice(pricePolicy.getDiscountPrice())
                .discountRate(pricePolicy.getDiscountRate())
                .accumulatedPoint(pricePolicy.getAccumulatedPoint())
                .build();
    }
}
