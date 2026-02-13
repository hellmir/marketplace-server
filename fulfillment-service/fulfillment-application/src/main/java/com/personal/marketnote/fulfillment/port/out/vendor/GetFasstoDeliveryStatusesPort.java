package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.delivery.FasstoDeliveryStatusQuery;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoDeliveryStatusesResult;

public interface GetFasstoDeliveryStatusesPort {
    GetFasstoDeliveryStatusesResult getDeliveryStatuses(FasstoDeliveryStatusQuery query);
}
