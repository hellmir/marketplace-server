package com.personal.marketnote.commerce.domain.order;

import java.util.Collections;
import java.util.List;

public enum OrderStatusFilter {
    SHIPPING(List.of(
            OrderStatus.PREPARING,
            OrderStatus.PREPARED,
            OrderStatus.SHIPPING,
            OrderStatus.EXCHANGE_SHIPPING,
            OrderStatus.REFUND_SHIPPING
    )),
    DELIVERED(List.of(OrderStatus.DELIVERED)),
    CONFIRMED(List.of(OrderStatus.CONFIRMED)),
    CANCEL_EXCHANGE_REFUND(List.of(
            OrderStatus.CANCEL_REQUESTED,
            OrderStatus.CANCELLED,
            OrderStatus.EXCHANGE_REQUESTED,
            OrderStatus.EXCHANGE_RECALLING,
            OrderStatus.EXCHANGE_SHIPPING,
            OrderStatus.EXCHANGED,
            OrderStatus.REFUND_REQUESTED,
            OrderStatus.REFUND_RECALLING,
            OrderStatus.REFUND_SHIPPING,
            OrderStatus.PARTIALLY_REFUNDED,
            OrderStatus.REFUNDED
    )),
    ALL(Collections.emptyList());

    private final List<OrderStatus> statuses;

    OrderStatusFilter(List<OrderStatus> statuses) {
        this.statuses = statuses;
    }

    public List<OrderStatus> toStatuses() {
        return statuses;
    }
}
