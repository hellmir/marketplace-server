package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.FasstoDeliveryStatusInfoResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoDeliveryStatusesResult;

import java.util.List;

public record GetFasstoDeliveryStatusesResponse(
        Integer dataCount,
        List<FasstoDeliveryStatusInfoResult> deliveryStatuses
) {
    public static GetFasstoDeliveryStatusesResponse from(GetFasstoDeliveryStatusesResult result) {
        return new GetFasstoDeliveryStatusesResponse(result.dataCount(), result.deliveryStatuses());
    }
}
