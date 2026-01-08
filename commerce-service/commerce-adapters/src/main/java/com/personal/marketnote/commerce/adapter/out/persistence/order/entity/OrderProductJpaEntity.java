package com.personal.marketnote.commerce.adapter.out.persistence.order.entity;

import com.personal.marketnote.commerce.domain.order.OrderProduct;
import com.personal.marketnote.commerce.domain.order.OrderStatus;
import com.personal.marketnote.common.adapter.out.persistence.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OrderProductJpaEntity extends BaseEntity {
    @EmbeddedId
    private OrderProductId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id", nullable = false)
    private OrderJpaEntity orderJpaEntity;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_amount", nullable = false)
    private Long unitAmount;

    @Column(name = "image_url", length = 511)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false, length = 31)
    private OrderStatus orderStatus;

    public static OrderProductJpaEntity from(OrderProduct orderProduct, OrderJpaEntity orderJpaEntity) {
        return OrderProductJpaEntity.builder()
                .id(new OrderProductId(orderProduct.getPricePolicyId(), orderJpaEntity.getId()))
                .orderJpaEntity(orderJpaEntity)
                .quantity(orderProduct.getQuantity())
                .unitAmount(orderProduct.getUnitAmount())
                .imageUrl(orderProduct.getImageUrl())
                .orderStatus(orderProduct.getOrderStatus())
                .build();
    }

    public void updateFrom(OrderProduct orderProduct) {
        quantity = orderProduct.getQuantity();
        unitAmount = orderProduct.getUnitAmount();
        imageUrl = orderProduct.getImageUrl();
        orderStatus = orderProduct.getOrderStatus();
    }
}

