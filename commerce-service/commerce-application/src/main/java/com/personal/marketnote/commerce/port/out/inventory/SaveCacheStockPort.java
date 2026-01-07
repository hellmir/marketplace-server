package com.personal.marketnote.commerce.port.out.inventory;

import com.personal.marketnote.commerce.domain.inventory.Inventory;

import java.util.Set;

public interface SaveCacheStockPort {
    void save(Set<Inventory> inventories);

    void save(Long pricePolicyId, int stock);
}
