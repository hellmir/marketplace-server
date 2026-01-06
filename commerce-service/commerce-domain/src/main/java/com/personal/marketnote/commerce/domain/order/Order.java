package com.personal.marketnote.product.domain.order;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Order {
    private Long id;
    private Long sellerId;
    private Long buyerId;
    private com.personal.marketnote.product.domain.order.OrderStatus orderStatus;
    private Long totalAmount;
    private Long paidAmount;
    private Long couponAmount;
    private Long pointAmount;
    private List<com.personal.marketnote.product.domain.order.OrderProduct> orderProducts;

    public static Order of(
            Long sellerId,
            Long buyerId,
            Long totalAmount,
            Long couponAmount,
            Long pointAmount,
            List<com.personal.marketnote.product.domain.order.OrderProduct> orderProducts
    ) {
        return Order.builder()
                .sellerId(sellerId)
                .buyerId(buyerId)
                .orderStatus(com.personal.marketnote.product.domain.order.OrderStatus.PAYMENT_PENDING)
                .totalAmount(totalAmount)
                .couponAmount(couponAmount)
                .pointAmount(pointAmount)
                .orderProducts(orderProducts)
                .build();
    }

    public static Order of(
            Long id,
            Long sellerId,
            Long buyerId,
            com.personal.marketnote.product.domain.order.OrderStatus orderStatus,
            Long totalAmount,
            Long paidAmount,
            Long couponAmount,
            Long pointAmount,
            List<com.personal.marketnote.product.domain.order.OrderProduct> orderProducts
    ) {
        return Order.builder()
                .id(id)
                .sellerId(sellerId)
                .buyerId(buyerId)
                .orderStatus(orderStatus)
                .totalAmount(totalAmount)
                .paidAmount(paidAmount)
                .couponAmount(couponAmount)
                .pointAmount(pointAmount)
                .orderProducts(orderProducts)
                .build();
    }

    public void changeProductsStatus(List<Long> pricePolicyIds, com.personal.marketnote.product.domain.order.OrderStatus orderStatus) {
        orderProducts.stream()
                .filter(orderProduct -> pricePolicyIds.contains(orderProduct.getPricePolicyId()))
                .forEach(orderProduct -> orderProduct.changeOrderStatus(orderStatus));

        if (orderStatus.isRefunded()) {
            this.orderStatus = com.personal.marketnote.product.domain.order.OrderStatus.getPartiallyRefunded();
            return;
        }

        this.orderStatus = orderStatus;
    }

    public void changeAllProductsStatus(com.personal.marketnote.product.domain.order.OrderStatus orderStatus) {
        orderProducts.forEach(orderProduct -> orderProduct.changeOrderStatus(orderStatus));
        this.orderStatus = orderStatus;
    }
}

