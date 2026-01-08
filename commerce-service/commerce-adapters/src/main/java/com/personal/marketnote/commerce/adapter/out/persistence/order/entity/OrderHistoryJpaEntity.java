package com.personal.marketnote.commerce.adapter.out.persistence.order.entity;

import com.personal.marketnote.commerce.domain.order.OrderHistory;
import com.personal.marketnote.commerce.domain.order.OrderStatus;
import com.personal.marketnote.common.adapter.out.persistence.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "order_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@DynamicInsert
@DynamicUpdate
public class OrderHistoryJpaEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderJpaEntity orderJpaEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false, length = 31)
    private OrderStatus orderStatus;

    @Column(name = "reason", nullable = false, length = 63)
    private String reason;

    public static OrderHistoryJpaEntity from(OrderJpaEntity orderJpaEntity) {
        OrderStatus orderStatus = orderJpaEntity.getOrderStatus();

        return OrderHistoryJpaEntity.builder()
                .orderJpaEntity(orderJpaEntity)
                .orderStatus(orderStatus)
                .reason(orderStatus.getDescription())
                .build();
    }

    public static OrderHistoryJpaEntity from(OrderHistory orderHistory, OrderJpaEntity orderJpaEntity) {
        return OrderHistoryJpaEntity.builder()
                .orderJpaEntity(orderJpaEntity)
                .orderStatus(orderHistory.getOrderStatus())
                .reason(orderHistory.getReason())
                .build();
    }
}

