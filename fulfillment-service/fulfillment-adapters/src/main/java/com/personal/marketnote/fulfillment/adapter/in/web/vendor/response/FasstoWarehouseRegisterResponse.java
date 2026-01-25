package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoWarehouseResult;

public record FasstoWarehouseRegisterResponse(
        RegisterFasstoWarehouseResult shopInfo
) {
    public static FasstoWarehouseRegisterResponse from(RegisterFasstoWarehouseResult shopInfo) {
        return new FasstoWarehouseRegisterResponse(shopInfo);
    }
}
