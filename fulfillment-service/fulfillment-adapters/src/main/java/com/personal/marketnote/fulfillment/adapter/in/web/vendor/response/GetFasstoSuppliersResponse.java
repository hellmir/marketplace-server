package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.FasstoSupplierInfoResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoSuppliersResult;

import java.util.List;

public record GetFasstoSuppliersResponse(
        Integer dataCount,
        List<FasstoSupplierInfoResult> suppliers
) {
    public static GetFasstoSuppliersResponse from(GetFasstoSuppliersResult result) {
        return new GetFasstoSuppliersResponse(result.dataCount(), result.suppliers());
    }
}
