package com.personal.marketnote.commerce.port.in.result.order;

import com.personal.marketnote.product.domain.order.Order;
import com.personal.marketnote.product.domain.order.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record GetOrderResult(
        Long id,
        Long sellerId,
        Long buyerId,
        OrderStatus orderStatus,
        Long totalAmount,
        Long paidAmount,
        Long couponAmount,
        Long pointAmount,
        List<GetOrderProductResult> orderProducts
) {
    public static GetOrderResult from(Order order) {
        return GetOrderResult.builder()
                .id(order.getId())
                .sellerId(order.getSellerId())
                .buyerId(order.getBuyerId())
                .orderStatus(order.getOrderStatus())
                .totalAmount(order.getTotalAmount())
                .paidAmount(order.getPaidAmount())
                .couponAmount(order.getCouponAmount())
                .pointAmount(order.getPointAmount())
                .orderProducts(order.getOrderProducts().stream().map(GetOrderProductResult::from).toList())
                .build();
    }
}
