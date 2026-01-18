package com.personal.marketnote.commerce.adapter.out.mapper;

import com.personal.marketnote.commerce.adapter.out.persistence.inventory.entity.InventoryJpaEntity;
import com.personal.marketnote.commerce.domain.inventory.Inventory;

import java.util.Optional;

public class InventoryJpaEntityToDomainMapper {
    public static Optional<Inventory> mapToDomain(InventoryJpaEntity inventoryJpaEntity) {
        return Optional.ofNullable(inventoryJpaEntity)
                .map(entity -> {
                    Long version = entity.getVersion();
                    if (version == null) {
                        version = 0L;
                    }
                    return Inventory.of(entity.getPricePolicyId(), entity.getStock(), version);
                });
    }
}

