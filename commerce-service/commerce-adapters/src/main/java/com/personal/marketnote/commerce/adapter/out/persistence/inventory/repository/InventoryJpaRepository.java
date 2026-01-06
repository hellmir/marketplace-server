package com.personal.marketnote.commerce.adapter.out.persistence.inventory.repository;

import com.personal.marketnote.commerce.adapter.out.persistence.inventory.entity.InventoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryJpaRepository extends JpaRepository<InventoryJpaEntity, Long> {
}

