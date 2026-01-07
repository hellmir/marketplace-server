package com.personal.marketnote.product.port.out.inventory;

import com.personal.marketnote.product.port.out.result.GetInventoryResult;

import java.util.List;
import java.util.Set;

public interface FindStockPort {
    Set<GetInventoryResult> findByPricePolicyIds(List<Long> pricePolicyIds);
}
