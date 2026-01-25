package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.FasstoWarehouseInfoResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousesResult;

import java.util.List;

public record GetFasstoWarehousesResponse(
        Integer dataCount,
        List<FasstoWarehouseInfoResult> warehouses
) {
    public static GetFasstoWarehousesResponse from(GetFasstoWarehousesResult result) {
        return new GetFasstoWarehousesResponse(result.dataCount(), result.warehouses());
    }
}
