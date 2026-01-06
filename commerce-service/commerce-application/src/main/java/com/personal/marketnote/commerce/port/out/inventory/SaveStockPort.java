package com.personal.marketnote.commerce.port.out.inventory;

public interface SaveStockPort {
    void save(Long pricePolicyId, int stock);
}

