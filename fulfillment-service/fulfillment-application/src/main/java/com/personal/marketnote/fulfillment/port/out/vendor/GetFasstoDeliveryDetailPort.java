package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.delivery.FasstoDeliveryDetailQuery;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoDeliveryDetailResult;

public interface GetFasstoDeliveryDetailPort {
    GetFasstoDeliveryDetailResult getDeliveryDetail(FasstoDeliveryDetailQuery query);
}
