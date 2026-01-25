package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.UpdateFasstoWarehouseResult;

public record FasstoWarehouseUpdateResponse(
        UpdateFasstoWarehouseResult shopInfo
) {
    public static FasstoWarehouseUpdateResponse from(UpdateFasstoWarehouseResult shopInfo) {
        return new FasstoWarehouseUpdateResponse(shopInfo);
    }
}
