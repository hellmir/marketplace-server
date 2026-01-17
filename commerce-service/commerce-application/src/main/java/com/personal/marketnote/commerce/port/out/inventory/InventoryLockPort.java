package com.personal.marketnote.commerce.port.out.inventory;

import java.util.Set;

public interface InventoryLockPort {
    void executeWithLock(Set<Long> pricePolicyIds, Runnable task);
}
