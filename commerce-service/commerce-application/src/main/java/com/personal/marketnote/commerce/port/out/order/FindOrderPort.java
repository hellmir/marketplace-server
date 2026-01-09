package com.personal.marketnote.commerce.port.out.order;

import com.personal.marketnote.commerce.domain.order.Order;
import com.personal.marketnote.commerce.domain.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FindOrderPort {
    Optional<Order> findById(Long id);

    List<Order> findByBuyerId(
            Long buyerId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            List<OrderStatus> statuses
    );
}
