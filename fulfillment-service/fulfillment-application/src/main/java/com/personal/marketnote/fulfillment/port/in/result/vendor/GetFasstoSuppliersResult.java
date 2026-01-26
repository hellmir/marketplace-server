package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record GetFasstoSuppliersResult(
        Integer dataCount,
        List<FasstoSupplierInfoResult> suppliers
) {
    public static GetFasstoSuppliersResult of(
            Integer dataCount,
            List<FasstoSupplierInfoResult> suppliers
    ) {
        return new GetFasstoSuppliersResult(dataCount, suppliers);
    }
}
