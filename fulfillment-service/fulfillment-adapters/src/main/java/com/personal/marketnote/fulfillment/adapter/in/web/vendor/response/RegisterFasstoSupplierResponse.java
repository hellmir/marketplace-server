package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoSupplierResult;

public record RegisterFasstoSupplierResponse(
        RegisterFasstoSupplierResult supplierInfo
) {
    public static RegisterFasstoSupplierResponse from(RegisterFasstoSupplierResult supplierInfo) {
        return new RegisterFasstoSupplierResponse(supplierInfo);
    }
}
