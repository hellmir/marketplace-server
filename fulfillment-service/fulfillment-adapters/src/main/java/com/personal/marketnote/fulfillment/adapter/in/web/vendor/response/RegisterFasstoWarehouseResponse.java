package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoWarehouseResult;

public record RegisterFasstoWarehouseResponse(
        RegisterFasstoWarehouseResult shopInfo
) {
    public static RegisterFasstoWarehouseResponse from(RegisterFasstoWarehouseResult shopInfo) {
        return new RegisterFasstoWarehouseResponse(shopInfo);
    }
}
