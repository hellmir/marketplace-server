package com.personal.marketnote.product.domain.order;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OrderProduct {
    private Long pricePolicyId;
    private Long orderId;
    private Integer quantity;
    private Long unitAmount;
    private String imageUrl;
    private com.personal.marketnote.product.domain.order.OrderStatus orderStatus;

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
                .orderStatus(com.personal.marketnote.product.domain.order.OrderStatus.PAYMENT_PENDING)
                .build();
    }

    public static OrderProduct of(
            Long pricePolicyId,
            Long orderId,
            Integer quantity,
            Long unitAmount,
            String imageUrl,
            com.personal.marketnote.product.domain.order.OrderStatus orderStatus
    ) {
        return OrderProduct.builder()
                .pricePolicyId(pricePolicyId)
                .orderId(orderId)
                .quantity(quantity)
                .unitAmount(unitAmount)
                .imageUrl(imageUrl)
                .orderStatus(orderStatus)
                .build();
    }

    public void changeOrderStatus(com.personal.marketnote.product.domain.order.OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}

