package com.personal.marketnote.product.domain.inventory;

import com.personal.marketnote.commerce.domain.inventory.WarehouseMapperCreateState;
import com.personal.marketnote.commerce.domain.inventory.WarehouseMapperSnapshotState;
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

    public static WarehouseMapper from(WarehouseMapperCreateState state) {
        return WarehouseMapper.builder()
                .productId(state.getProductId())
                .wmsKey(state.getWmsKey())
                .wmsProductKey(state.getWmsProductKey())
                .stock(state.getStock())
                .build();
    }

    public static WarehouseMapper from(WarehouseMapperSnapshotState state) {
        return WarehouseMapper.builder()
                .productId(state.getProductId())
                .wmsKey(state.getWmsKey())
                .wmsProductKey(state.getWmsProductKey())
                .stock(state.getStock())
                .build();
    }
}

