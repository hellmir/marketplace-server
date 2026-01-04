package com.personal.marketnote.product.adapter.out.persistence.cart.repository;

import com.personal.marketnote.product.adapter.out.persistence.cart.entity.CartProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartJpaRepository extends JpaRepository<CartProductJpaEntity, Long> {
    List<CartProductJpaEntity> findByIdUserId(Long userId);
}
