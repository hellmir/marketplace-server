package com.personal.marketnote.commerce.adapter.out.persistence.order.repository;

import com.personal.marketnote.commerce.adapter.out.persistence.order.entity.OrderStatusHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryJpaRepository extends JpaRepository<OrderStatusHistoryJpaEntity, Long> {
    OrderStatusHistoryJpaEntity findTopByOrderJpaEntityIdOrderByIdDesc(Long orderId);
}
