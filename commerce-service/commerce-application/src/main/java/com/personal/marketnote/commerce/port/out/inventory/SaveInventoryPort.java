package com.personal.marketnote.commerce.port.out.inventory;

import com.personal.marketnote.commerce.domain.inventory.Inventory;

import java.util.Set;

public interface SaveInventoryPort {
    void save(Inventory inventory);

    void save(Set<Inventory> inventories);
}

