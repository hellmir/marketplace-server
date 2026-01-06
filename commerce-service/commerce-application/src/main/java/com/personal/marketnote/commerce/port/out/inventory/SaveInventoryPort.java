package com.personal.marketnote.commerce.port.out.inventory;

import com.personal.marketnote.product.domain.inventory.Inventory;

public interface SaveInventoryPort {
    void save(Inventory inventory);
}

