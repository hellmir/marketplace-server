package com.personal.marketnote.product.domain.inventory;

import com.personal.marketnote.commerce.domain.inventory.Stock;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class InventoryAdditionHistory {
    private Long id;
    private Long pricePolicyId;
    private Stock stock;
    private String reason;
    private Long unitPrice;
    private String supplier;

    public static InventoryAdditionHistory of(
            Long pricePolicyId,
            Integer stock,
            String reason,
            Long unitPrice,
            String supplier
    ) {
        return InventoryAdditionHistory.builder()
                .pricePolicyId(pricePolicyId)
                .stock(Stock.of(stock.toString()))
                .reason(reason)
                .unitPrice(unitPrice)
                .supplier(supplier)
                .build();
    }

    public static InventoryAdditionHistory of(
            Long id,
            Long pricePolicyId,
            Integer stock,
            String reason,
            Long unitPrice,
            String supplier
    ) {
        return InventoryAdditionHistory.builder()
                .id(id)
                .pricePolicyId(pricePolicyId)
                .stock(Stock.of(stock.toString()))
                .reason(reason)
                .unitPrice(unitPrice)
                .supplier(supplier)
                .build();
    }
}

