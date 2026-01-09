package com.personal.marketnote.commerce.domain.order;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OrderProduct {
    private Long orderId;
    private Long pricePolicyId;
    private Integer quantity;
    private Long unitAmount;
    private String imageUrl;
    private OrderStatus orderStatus;

    public static OrderProduct of(
            Long pricePolicyId,
            Integer quantity,
            Long unitAmount,
            String imageUrl
    ) {
        return OrderProduct.builder()
                .pricePolicyId(pricePolicyId)
                .quantity(quantity)
                .unitAmount(unitAmount)
                .imageUrl(imageUrl)
                .orderStatus(OrderStatus.PAYMENT_PENDING)
                .build();
    }

    public static OrderProduct of(
            Long orderId,
            Long pricePolicyId,
            Integer quantity,
            Long unitAmount,
            String imageUrl,
            OrderStatus orderStatus
    ) {
        return OrderProduct.builder()
                .orderId(orderId)
                .pricePolicyId(pricePolicyId)
                .quantity(quantity)
                .unitAmount(unitAmount)
                .imageUrl(imageUrl)
                .orderStatus(orderStatus)
                .build();
    }

    public void changeOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}

