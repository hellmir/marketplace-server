package com.personal.marketnote.commerce.adapter.out.persistence.order.repository;

import com.personal.marketnote.commerce.adapter.out.persistence.order.entity.OrderHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryJpaRepository extends JpaRepository<OrderHistoryJpaEntity, Long> {
}

