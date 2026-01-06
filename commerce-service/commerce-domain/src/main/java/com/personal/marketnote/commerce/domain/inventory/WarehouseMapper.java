package com.personal.marketnote.product.domain.inventory;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class WarehouseMapper {
    private Long productId;
    private String wmsKey;
    private String wmsProductKey;
    private Integer stock;

    public static WarehouseMapper of(
            String wmsKey,
            String wmsProductKey,
            Integer stock
    ) {
        return WarehouseMapper.builder()
                .wmsKey(wmsKey)
                .wmsProductKey(wmsProductKey)
                .stock(stock)
                .build();
    }

    public static WarehouseMapper of(
            Long productId,
            String wmsKey,
            String wmsProductKey,
            Integer stock
    ) {
        return WarehouseMapper.builder()
                .productId(productId)
                .wmsKey(wmsKey)
                .wmsProductKey(wmsProductKey)
                .stock(stock)
                .build();
    }
}

