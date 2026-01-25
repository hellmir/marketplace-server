package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.warehouse.FasstoWarehouseMapper;
import com.personal.marketnote.fulfillment.port.in.result.vendor.UpdateFasstoWarehouseResult;

public interface UpdateFasstoWarehousePort {
    UpdateFasstoWarehouseResult updateWarehouse(FasstoWarehouseMapper request);
}
