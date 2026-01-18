package com.personal.marketnote.product.domain.cart;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CartProductSnapshotState {
    private final Long userId;
    private final Long sharerId;
    private final PricePolicy pricePolicy;
    private final String imageUrl;
    private final Short quantity;
    private final EntityStatus status;
}

