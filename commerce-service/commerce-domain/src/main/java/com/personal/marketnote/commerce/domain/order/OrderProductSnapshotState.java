package com.personal.marketnote.commerce.domain.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderProductSnapshotState {
    private final Long orderId;
    private final Long pricePolicyId;
    private final Integer quantity;
    private final Long unitAmount;
    private final String imageUrl;
    private final OrderStatus orderStatus;
}

