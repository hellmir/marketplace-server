package com.personal.marketnote.order.adapter.out.persistence.order.repository;

import com.personal.marketnote.order.adapter.out.persistence.order.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, Long> {
}

