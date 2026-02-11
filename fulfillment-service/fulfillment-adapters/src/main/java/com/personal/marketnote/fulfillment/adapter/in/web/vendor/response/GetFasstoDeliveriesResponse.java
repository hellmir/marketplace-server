package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.FasstoDeliveryInfoResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoDeliveriesResult;

import java.util.List;

public record GetFasstoDeliveriesResponse(
        Integer dataCount,
        List<FasstoDeliveryInfoResult> deliveries
) {
    public static GetFasstoDeliveriesResponse from(GetFasstoDeliveriesResult result) {
        return new GetFasstoDeliveriesResponse(result.dataCount(), result.deliveries());
    }
}
