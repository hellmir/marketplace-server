package com.personal.marketnote.product.port.out.inventory;

public interface SaveCacheStockPort {
    void save(Long pricePolicyId, int stock);
}
