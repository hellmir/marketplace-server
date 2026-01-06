package com.personal.marketnote.product.domain.inventory;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class InventoryAdditionHistory {
    private Long id;
    private Long pricePolicyId;
    private Integer quantity;
    private String reason;
    private Long unitPrice;
    private String supplier;

    public static InventoryAdditionHistory of(
            Long pricePolicyId,
            Integer quantity,
            String reason,
            Long unitPrice,
            String supplier
    ) {
        return InventoryAdditionHistory.builder()
                .pricePolicyId(pricePolicyId)
                .quantity(quantity)
                .reason(reason)
                .unitPrice(unitPrice)
                .supplier(supplier)
                .build();
    }

    public static InventoryAdditionHistory of(
            Long id,
            Long pricePolicyId,
            Integer quantity,
            String reason,
            Long unitPrice,
            String supplier
    ) {
        return InventoryAdditionHistory.builder()
                .id(id)
                .pricePolicyId(pricePolicyId)
                .quantity(quantity)
                .reason(reason)
                .unitPrice(unitPrice)
                .supplier(supplier)
                .build();
    }
}

