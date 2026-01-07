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

    public static InventoryDeductionHistory of(
            Long pricePolicyId,
            Integer stock,
            String reason
    ) {
        return InventoryDeductionHistory.builder()
                .pricePolicyId(pricePolicyId)
                .stock(Stock.of(stock.toString()))
                .reason(reason)
                .build();
    }

    public static InventoryDeductionHistory of(
            Long id,
            Long pricePolicyId,
            Integer stock,
            String reason
    ) {
        return InventoryDeductionHistory.builder()
                .id(id)
                .pricePolicyId(pricePolicyId)
                .stock(Stock.of(stock.toString()))
                .reason(reason)
                .build();
    }

    public Integer getStockValue() {
        return stock.getValue();
    }
}

