package com.personal.marketnote.commerce.adapter.out.persistence.order.entity;

import com.personal.marketnote.commerce.domain.order.Order;
import com.personal.marketnote.commerce.domain.order.OrderProduct;
import com.personal.marketnote.commerce.domain.order.OrderStatus;
import com.personal.marketnote.common.adapter.out.persistence.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@DynamicInsert
@DynamicUpdate
public class OrderJpaEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false, length = 31)
    private OrderStatus orderStatus;

    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    @Column(name = "paid_amount")
    private Long paidAmount;

    @Column(name = "coupon_amount")
    private Long couponAmount;

    @Column(name = "point_amount")
    private Long pointAmount;

    @OneToMany(mappedBy = "orderJpaEntity", cascade = {PERSIST, MERGE}, orphanRemoval = true)
    @Builder.Default
    private List<OrderProductJpaEntity> orderProductJpaEntities = new ArrayList<>();

    public static OrderJpaEntity from(Order order) {
        return OrderJpaEntity.builder()
                .sellerId(order.getSellerId())
                .buyerId(order.getBuyerId())
                .orderStatus(order.getOrderStatus())
                .totalAmount(order.getTotalAmount())
                .paidAmount(order.getPaidAmount())
                .couponAmount(order.getCouponAmount())
                .pointAmount(order.getPointAmount())
                .build();
    }

    public void addOrderProduct(OrderProductJpaEntity orderProductJpaEntity) {
        this.orderProductJpaEntities.add(orderProductJpaEntity);
    }

    public void updateFrom(Order order) {
        orderStatus = order.getOrderStatus();
        totalAmount = order.getTotalAmount();
        paidAmount = order.getPaidAmount();
        couponAmount = order.getCouponAmount();
        pointAmount = order.getPointAmount();

        List<OrderProduct> orderProducts = order.getOrderProducts();
        Map<Long, OrderProduct> orderProductMap = orderProducts.stream()
                .collect(Collectors.toMap(
                        OrderProduct::getPricePolicyId,
                        op -> op
                ));

        orderProductJpaEntities.forEach(entity -> {
            OrderProduct matched = orderProductMap.get(entity.getId().getPricePolicyId());
            if (matched != null) {
                entity.updateFrom(matched);
            }
        });
    }
}

