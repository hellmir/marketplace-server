package com.personal.marketnote.product.domain.inventory;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class InventoryDeductionHistory {
    private Long id;
    private Long pricePolicyId;
    private Integer quantity;
    private String reason;

    public static InventoryDeductionHistory of(
            Long pricePolicyId,
            Integer quantity,
            String reason
    ) {
        return InventoryDeductionHistory.builder()
                .pricePolicyId(pricePolicyId)
                .quantity(quantity)
                .reason(reason)
                .build();
    }

    public static InventoryDeductionHistory of(
            Long id,
            Long pricePolicyId,
            Integer quantity,
            String reason
    ) {
        return InventoryDeductionHistory.builder()
                .id(id)
                .pricePolicyId(pricePolicyId)
                .quantity(quantity)
                .reason(reason)
                .build();
    }
}

