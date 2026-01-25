package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.warehouse.FasstoWarehouseQuery;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousesResult;

public interface GetFasstoWarehousesPort {
    GetFasstoWarehousesResult getWarehouses(FasstoWarehouseQuery query);
}
