package com.personal.marketnote.commerce.adapter.out.mapper;

import com.personal.marketnote.commerce.adapter.out.persistence.inventory.entity.InventoryJpaEntity;
import com.personal.marketnote.commerce.domain.inventory.Inventory;

import java.util.Optional;

public class InventoryJpaEntityToDomainMapper {
    public static Optional<Inventory> mapToDomain(InventoryJpaEntity inventoryJpaEntity) {
        return Optional.ofNullable(inventoryJpaEntity)
                .map(entity -> Inventory.of(entity.getPricePolicyId(), entity.getQuantity()));
    }
}

