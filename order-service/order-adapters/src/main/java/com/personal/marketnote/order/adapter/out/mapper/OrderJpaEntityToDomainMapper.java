package com.personal.marketnote.order.adapter.out.mapper;

import com.personal.marketnote.order.adapter.out.persistence.order.entity.OrderJpaEntity;
import com.personal.marketnote.order.adapter.out.persistence.order.entity.OrderProductJpaEntity;
import com.personal.marketnote.product.domain.order.Order;
import com.personal.marketnote.product.domain.order.OrderProduct;

import java.util.List;
import java.util.Optional;

public class OrderJpaEntityToDomainMapper {
    public static Optional<Order> mapToDomain(OrderJpaEntity orderJpaEntity) {
        return Optional.ofNullable(orderJpaEntity)
                .map(entity -> {
                    List<OrderProduct> orderProducts = entity.getOrderProductJpaEntities().stream()
                            .map(OrderJpaEntityToDomainMapper::mapToDomain)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .toList();

                    return Order.of(
                            entity.getId(),
                            entity.getSellerId(),
                            entity.getBuyerId(),
                            entity.getOrderStatus(),
                            entity.getTotalAmount(),
                            entity.getPaidAmount(),
                            entity.getCouponAmount(),
                            entity.getPointAmount(),
                            orderProducts
                    );
                });
    }

    private static Optional<OrderProduct> mapToDomain(OrderProductJpaEntity orderProductJpaEntity) {
        return Optional.ofNullable(orderProductJpaEntity)
                .map(entity -> OrderProduct.of(
                        entity.getId().getPricePolicyId(),
                        entity.getId().getOrderId(),
                        entity.getQuantity(),
                        entity.getUnitAmount(),
                        entity.getImageUrl(),
                        entity.getOrderStatus()
                ));
    }
}

