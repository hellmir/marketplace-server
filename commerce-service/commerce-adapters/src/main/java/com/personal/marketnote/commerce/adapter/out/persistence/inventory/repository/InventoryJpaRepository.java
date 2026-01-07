package com.personal.marketnote.commerce.adapter.out.persistence.inventory.repository;

import com.personal.marketnote.commerce.adapter.out.persistence.inventory.entity.InventoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface InventoryJpaRepository extends JpaRepository<InventoryJpaEntity, Long> {
    Optional<InventoryJpaEntity> findByPricePolicyId(Long pricePolicyId);

    @Query("""
            SELECT i FROM InventoryJpaEntity i
            WHERE i.pricePolicyId
            IN :pricePolicyIds
            ORDER BY i.pricePolicyId ASC
            """)
    Set<InventoryJpaEntity> findByPricePolicyIds(Set<Long> pricePolicyIds);
}

