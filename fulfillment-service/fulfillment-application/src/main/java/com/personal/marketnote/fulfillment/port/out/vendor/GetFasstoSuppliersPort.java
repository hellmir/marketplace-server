package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.supplier.FasstoSupplierQuery;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoSuppliersResult;

public interface GetFasstoSuppliersPort {
    GetFasstoSuppliersResult getSuppliers(FasstoSupplierQuery query);
}
