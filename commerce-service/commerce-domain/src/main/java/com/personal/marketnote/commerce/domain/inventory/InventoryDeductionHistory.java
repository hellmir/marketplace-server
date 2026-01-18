package com.personal.marketnote.commerce.domain.inventory;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class InventoryDeductionHistory {
    private Long id;
    private Long pricePolicyId;
    private Stock stock;
    private String reason;

    public static InventoryDeductionHistory from(InventoryDeductionHistoryCreateState state) {
        return InventoryDeductionHistory.builder()
                .pricePolicyId(state.getPricePolicyId())
                .stock(Stock.of(
                        String.valueOf(state.getStock())
                ))
                .reason(state.getReason())
                .build();
    }

    public static InventoryDeductionHistory from(InventoryDeductionHistorySnapshotState state) {
        return InventoryDeductionHistory.builder()
                .id(state.getId())
                .pricePolicyId(state.getPricePolicyId())
                .stock(Stock.of(
                        String.valueOf(state.getStock())
                ))
                .reason(state.getReason())
                .build();
    }

    public Integer getStockValue() {
        return stock.getValue();
    }
}

