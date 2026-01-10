package com.personal.marketnote.product.domain.cart;

import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CartProductCreateState {
    private final Long userId;
    private final PricePolicy pricePolicy;
    private final String imageUrl;
    private final Short quantity;
}

