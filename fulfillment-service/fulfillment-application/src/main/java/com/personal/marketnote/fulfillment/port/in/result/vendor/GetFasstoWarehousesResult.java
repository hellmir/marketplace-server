package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record GetFasstoWarehousesResult(
        Integer dataCount,
        List<FasstoWarehouseInfoResult> warehouses
) {
    public static GetFasstoWarehousesResult of(
            Integer dataCount,
            List<FasstoWarehouseInfoResult> warehouses
    ) {
        return new GetFasstoWarehousesResult(dataCount, warehouses);
    }
}
