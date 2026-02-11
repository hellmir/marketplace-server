package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.FasstoDeliveryDetailInfoResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoDeliveryDetailResult;

import java.util.List;

public record GetFasstoDeliveryDetailResponse(
        Integer dataCount,
        List<FasstoDeliveryDetailInfoResult> deliveries
) {
    public static GetFasstoDeliveryDetailResponse from(GetFasstoDeliveryDetailResult result) {
        return new GetFasstoDeliveryDetailResponse(result.dataCount(), result.deliveries());
    }
}
