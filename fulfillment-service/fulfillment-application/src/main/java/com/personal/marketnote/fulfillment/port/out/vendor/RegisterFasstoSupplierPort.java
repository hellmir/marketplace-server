package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.supplier.FasstoSupplierMapper;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoSupplierResult;

public interface RegisterFasstoSupplierPort {
    RegisterFasstoSupplierResult registerSupplier(FasstoSupplierMapper request);
}
