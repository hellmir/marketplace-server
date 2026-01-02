package com.personal.marketnote.product.adapter.out.persistence.pricepolicy.repository;

import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricePolicyJpaRepository extends JpaRepository<PricePolicyJpaEntity, Long> {
    boolean existsByIdAndProductJpaEntity_Id(Long id, Long productId);
}


