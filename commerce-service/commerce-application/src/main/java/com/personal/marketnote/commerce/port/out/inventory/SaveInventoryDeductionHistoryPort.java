package com.personal.marketnote.commerce.port.out.inventory;

import com.personal.marketnote.commerce.domain.inventory.InventoryDeductionHistories;

public interface SaveInventoryDeductionHistoryPort {
    void save(InventoryDeductionHistories inventoryDeductionHistories);
}
