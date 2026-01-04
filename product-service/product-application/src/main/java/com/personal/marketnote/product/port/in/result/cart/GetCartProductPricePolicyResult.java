package com.personal.marketnote.product.port.in.result.cart;

import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import lombok.AccessLevel;
import lombok.Builder;

import java.math.BigDecimal;

@Builder(access = AccessLevel.PRIVATE)
public record GetCartProductPricePolicyResult(
        Long id,
        Long price,
        Long discountPrice,
        Long accumulatedPoint,
        BigDecimal discountRate
) {
    public static GetCartProductPricePolicyResult from(PricePolicy pricePolicy) {
        return GetCartProductPricePolicyResult.builder()
                .id(pricePolicy.getId())
                .price(pricePolicy.getPrice())
                .discountPrice(pricePolicy.getDiscountPrice())
                .accumulatedPoint(pricePolicy.getAccumulatedPoint())
                .discountRate(pricePolicy.getDiscountRate())
                .build();
    }
}
