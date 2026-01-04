package com.personal.marketnote.product.adapter.out.persistence.cart.repository;

import com.personal.marketnote.product.adapter.out.persistence.cart.entity.CartProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartJpaRepository extends JpaRepository<CartProductJpaEntity, Long> {
}
