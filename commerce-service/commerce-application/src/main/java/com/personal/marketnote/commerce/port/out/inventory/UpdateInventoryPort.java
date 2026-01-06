package com.personal.marketnote.commerce.port.out.inventory;

import com.personal.marketnote.commerce.domain.inventory.Inventory;
import com.personal.marketnote.commerce.exception.InventoryNotFoundException;

import java.util.Set;

public interface UpdateInventoryPort {
    void update(Set<Inventory> inventories) throws InventoryNotFoundException;
}
