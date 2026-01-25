package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.UpdateFasstoWarehouseResult;

public record UpdateFasstoWarehouseResponse(
        UpdateFasstoWarehouseResult shopInfo
) {
    public static UpdateFasstoWarehouseResponse from(UpdateFasstoWarehouseResult shopInfo) {
        return new UpdateFasstoWarehouseResponse(shopInfo);
    }
}
