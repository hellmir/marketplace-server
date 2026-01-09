package com.personal.marketnote.commerce.domain.order;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OrderStatusHistory {
    private Long id;
    private Long orderId;
    private OrderStatus orderStatus;
    private String reason;
    private LocalDateTime createdAt;

    public static OrderStatusHistory of(
            Long orderId,
            OrderStatus orderStatus,
            String reason
    ) {
        if (!FormatValidator.hasValue(reason)) {
            reason = orderStatus.getDescription();
        }

        return OrderStatusHistory.builder()
                .orderId(orderId)
                .orderStatus(orderStatus)
                .reason(reason)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static OrderStatusHistory of(
            Long id,
            Long orderId,
            OrderStatus orderStatus,
            String reason,
            LocalDateTime createdAt
    ) {
        return OrderStatusHistory.builder()
                .id(id)
                .orderId(orderId)
                .orderStatus(orderStatus)
                .reason(reason)
                .createdAt(createdAt)
                .build();
    }
}

