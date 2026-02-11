package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.delivery.FasstoDeliveryQuery;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoDeliveriesResult;

public interface GetFasstoDeliveriesPort {
    GetFasstoDeliveriesResult getDeliveries(FasstoDeliveryQuery query);
}
