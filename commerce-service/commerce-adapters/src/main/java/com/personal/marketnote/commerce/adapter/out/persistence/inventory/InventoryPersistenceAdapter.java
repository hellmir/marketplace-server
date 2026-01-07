package com.personal.marketnote.commerce.adapter.out.persistence.inventory;

import com.personal.marketnote.commerce.adapter.out.mapper.InventoryJpaEntityToDomainMapper;
import com.personal.marketnote.commerce.adapter.out.persistence.inventory.entity.InventoryDeductionHistoryJpaEntity;
import com.personal.marketnote.commerce.adapter.out.persistence.inventory.entity.InventoryJpaEntity;
import com.personal.marketnote.commerce.adapter.out.persistence.inventory.repository.InventoryDeductionHistoryJpaRepository;
import com.personal.marketnote.commerce.adapter.out.persistence.inventory.repository.InventoryJpaRepository;
import com.personal.marketnote.commerce.domain.inventory.Inventory;
import com.personal.marketnote.commerce.domain.inventory.InventoryDeductionHistories;
import com.personal.marketnote.commerce.exception.InventoryNotFoundException;
import com.personal.marketnote.commerce.port.out.inventory.FindInventoryPort;
import com.personal.marketnote.commerce.port.out.inventory.SaveInventoryDeductionHistoryPort;
import com.personal.marketnote.commerce.port.out.inventory.SaveInventoryPort;
import com.personal.marketnote.commerce.port.out.inventory.UpdateInventoryPort;
import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class InventoryPersistenceAdapter implements SaveInventoryPort, FindInventoryPort, UpdateInventoryPort, SaveInventoryDeductionHistoryPort {
    private final InventoryJpaRepository inventoryJpaRepository;
    private final InventoryDeductionHistoryJpaRepository inventoryDeductionHistoryJpaRepository;

    @Override
    public void save(Inventory inventory) {
        InventoryJpaEntity inventoryEntity = InventoryJpaEntity.from(inventory);
        inventoryJpaRepository.save(inventoryEntity);
    }

    @Override
    public Set<Inventory> findByPricePolicyIds(Set<Long> pricePolicyIds) {
        return inventoryJpaRepository.findByPricePolicyIds(pricePolicyIds)
                .stream()
                .map(InventoryJpaEntityToDomainMapper::mapToDomain)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    @Override
    public void update(Set<Inventory> inventories) throws InventoryNotFoundException {
        Set<InventoryJpaEntity> inventoryEntities = inventoryJpaRepository.findByPricePolicyIds(
                inventories.stream()
                        .map(Inventory::getPricePolicyId)
                        .collect(Collectors.toSet())
        );

        for (Inventory inventory : inventories) {
            InventoryJpaEntity inventoryEntity = inventoryEntities.stream()
                    .filter(entity -> entity.getPricePolicyId().equals(inventory.getPricePolicyId()))
                    .findFirst()
                    .orElseThrow(() -> new InventoryNotFoundException(inventory.getPricePolicyId()));
            inventoryEntity.updateFrom(inventory);
        }
    }

    @Override
    public void save(InventoryDeductionHistories inventoryDeductionHistories) {
        inventoryDeductionHistoryJpaRepository.saveAll(
                inventoryDeductionHistories.getInventoryDeductionHistories()
                        .stream()
                        .map(InventoryDeductionHistoryJpaEntity::from)
                        .toList()
        );
    }
}
