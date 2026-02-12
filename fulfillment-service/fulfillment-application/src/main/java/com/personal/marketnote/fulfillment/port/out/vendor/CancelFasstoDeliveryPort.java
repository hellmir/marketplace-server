package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.delivery.FasstoDeliveryCancelMapper;
import com.personal.marketnote.fulfillment.port.in.result.vendor.CancelFasstoDeliveryResult;

public interface CancelFasstoDeliveryPort {
    CancelFasstoDeliveryResult cancelDelivery(FasstoDeliveryCancelMapper request);
}
