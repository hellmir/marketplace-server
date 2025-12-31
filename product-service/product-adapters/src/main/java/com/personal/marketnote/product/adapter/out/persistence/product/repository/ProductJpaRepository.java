package com.personal.marketnote.product.adapter.out.persistence.product.repository;

import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {
    boolean existsByIdAndSellerId(Long productId, Long sellerId);
}


