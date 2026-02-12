package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.CancelFasstoDeliveryItemResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.CancelFasstoDeliveryResult;

import java.util.List;

public record CancelFasstoDeliveryResponse(
        Integer dataCount,
        List<CancelFasstoDeliveryItemResult> deliveries
) {
    public static CancelFasstoDeliveryResponse from(CancelFasstoDeliveryResult result) {
        return new CancelFasstoDeliveryResponse(result.dataCount(), result.deliveries());
    }
}
