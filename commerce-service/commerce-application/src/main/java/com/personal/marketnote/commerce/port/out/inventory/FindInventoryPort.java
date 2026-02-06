package com.personal.marketnote.commerce.port.out.inventory;

import com.personal.marketnote.commerce.domain.inventory.Inventory;

import java.util.Set;

public interface FindInventoryPort {
    Set<Inventory> findByPricePolicyIds(Set<Long> pricePolicyIds);

    Set<Inventory> findByProductIds(Set<Long> productIds);
}
