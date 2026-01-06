package com.personal.marketnote.commerce.adapter.out.persistence.inventory;

import com.personal.marketnote.commerce.adapter.out.persistence.inventory.entity.InventoryJpaEntity;
import com.personal.marketnote.commerce.adapter.out.persistence.inventory.repository.InventoryJpaRepository;
import com.personal.marketnote.commerce.port.out.inventory.SaveInventoryPort;
import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.product.domain.inventory.Inventory;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class InventoryPersistenceAdapter implements SaveInventoryPort {
    private final InventoryJpaRepository inventoryJpaRepository;

    @Override
    public void save(Inventory inventory) {
        InventoryJpaEntity inventoryEntity = InventoryJpaEntity.from(inventory);
        inventoryJpaRepository.save(inventoryEntity);
    }
}
