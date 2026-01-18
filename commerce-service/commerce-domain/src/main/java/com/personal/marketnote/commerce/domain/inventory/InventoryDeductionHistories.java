package com.personal.marketnote.commerce.domain.inventory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class InventoryDeductionHistories {
    private List<InventoryDeductionHistory> inventoryDeductionHistories;

    public static InventoryDeductionHistories from(Map<Long, Integer> stocksByPricePolicyId, String reason) {
        return new InventoryDeductionHistories(
                stocksByPricePolicyId.entrySet()
                        .stream()
                        .map(entry -> InventoryDeductionHistory.from(
                                InventoryDeductionHistoryCreateState.builder()
                                        .pricePolicyId(entry.getKey())
                                        .stock(entry.getValue())
                                        .reason(reason)
                                        .build()
                        ))
                        .toList()
        );
    }
}

