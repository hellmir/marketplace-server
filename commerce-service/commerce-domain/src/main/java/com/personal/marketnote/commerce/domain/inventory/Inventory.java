package com.personal.marketnote.product.domain.inventory;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Inventory {
    private Long pricePolicyId;
    private Integer quantity;

    public static Inventory of(Integer quantity) {
        return Inventory.builder()
                .quantity(quantity)
                .build();
    }

    public static Inventory of(Long pricePolicyId, Integer quantity) {
        return Inventory.builder()
                .pricePolicyId(pricePolicyId)
                .quantity(quantity)
                .build();
    }
}

