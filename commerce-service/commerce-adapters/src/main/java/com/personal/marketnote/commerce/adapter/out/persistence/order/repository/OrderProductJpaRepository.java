package com.personal.marketnote.commerce.adapter.out.persistence.order.repository;

import com.personal.marketnote.commerce.adapter.out.persistence.order.entity.OrderProductId;
import com.personal.marketnote.commerce.adapter.out.persistence.order.entity.OrderProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductJpaRepository extends JpaRepository<OrderProductJpaEntity, OrderProductId> {
}

