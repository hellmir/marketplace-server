package com.personal.marketnote.product.port.out.inventory;

public interface RegisterInventoryPort {
    void registerInventory(Long productId, Long pricePolicyId);
}
