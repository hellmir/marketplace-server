package com.personal.marketnote.commerce.adapter.out.mapper;

import com.personal.marketnote.commerce.adapter.out.persistence.order.entity.OrderJpaEntity;
import com.personal.marketnote.commerce.adapter.out.persistence.order.entity.OrderProductJpaEntity;
import com.personal.marketnote.commerce.domain.order.Order;
import com.personal.marketnote.commerce.domain.order.OrderProduct;
import com.personal.marketnote.commerce.domain.order.OrderProductSnapshotState;
import com.personal.marketnote.commerce.domain.order.OrderSnapshotState;

import java.util.List;
import java.util.Optional;

public class OrderJpaEntityToDomainMapper {
    public static Optional<Order> mapToDomain(OrderJpaEntity orderJpaEntity) {
        return Optional.ofNullable(orderJpaEntity)
                .map(entity -> {
                    List<OrderProductSnapshotState> productStates = entity.getOrderProductJpaEntities().stream()
                            .map(OrderJpaEntityToDomainMapper::mapToSnapshotState)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .toList();

                    return Order.from(
                            OrderSnapshotState.builder()
                                    .id(entity.getId())
                                    .sellerId(entity.getSellerId())
                                    .buyerId(entity.getBuyerId())
                                    .orderStatus(entity.getOrderStatus())
                                    .totalAmount(entity.getTotalAmount())
                                    .paidAmount(entity.getPaidAmount())
                                    .couponAmount(entity.getCouponAmount())
                                    .pointAmount(entity.getPointAmount())
                                    .orderProductStates(productStates)
                                    .createdAt(entity.getCreatedAt())
                                    .modifiedAt(entity.getModifiedAt())
                                    .build()
                    );
                });
    }

    private static Optional<OrderProductSnapshotState> mapToSnapshotState(OrderProductJpaEntity orderProductJpaEntity) {
        return Optional.ofNullable(orderProductJpaEntity)
                .map(entity -> OrderProductSnapshotState.builder()
                        .orderId(entity.getId().getOrderId())
                        .pricePolicyId(entity.getId().getPricePolicyId())
                        .quantity(entity.getQuantity())
                        .unitAmount(entity.getUnitAmount())
                        .imageUrl(entity.getImageUrl())
                        .orderStatus(entity.getOrderStatus())
                        .isReviewed(entity.getIsReviewed())
                        .build());
    }

    public static Optional<OrderProduct> mapToDomain(OrderProductJpaEntity orderProductJpaEntity) {
        return Optional.ofNullable(orderProductJpaEntity)
                .map(entity -> OrderProduct.from(
                        OrderProductSnapshotState.builder()
                                .orderId(entity.getId().getOrderId())
                                .pricePolicyId(entity.getId().getPricePolicyId())
                                .quantity(entity.getQuantity())
                                .unitAmount(entity.getUnitAmount())
                                .imageUrl(entity.getImageUrl())
                                .orderStatus(entity.getOrderStatus())
                                .isReviewed(entity.getIsReviewed())
                                .build()
                ));
    }
}
