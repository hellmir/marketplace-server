package com.personal.marketnote.commerce.domain.inventory;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Inventory {
    private Long pricePolicyId;
    private InventoryQuantity quantity;

    public static Inventory of(Long pricePolicyId) {
        return Inventory.builder()
                .pricePolicyId(pricePolicyId)
                .build();
    }

    public static Inventory of(Long pricePolicyId, Integer quantity) {
        return Inventory.builder()
                .pricePolicyId(pricePolicyId)
                .quantity(InventoryQuantity.of(quantity.toString()))
                .build();
    }

    public void reduce(int quantityToReduce) {
        Integer reducedQuantity = quantity.reduce(quantityToReduce);
        quantity = InventoryQuantity.of(reducedQuantity.toString());
    }
}

