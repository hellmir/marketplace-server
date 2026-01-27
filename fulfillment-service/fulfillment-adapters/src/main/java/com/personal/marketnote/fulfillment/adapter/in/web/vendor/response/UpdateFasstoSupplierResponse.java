package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.UpdateFasstoSupplierResult;

public record UpdateFasstoSupplierResponse(
        UpdateFasstoSupplierResult supplierInfo
) {
    public static UpdateFasstoSupplierResponse from(UpdateFasstoSupplierResult supplierInfo) {
        return new UpdateFasstoSupplierResponse(supplierInfo);
    }
}
