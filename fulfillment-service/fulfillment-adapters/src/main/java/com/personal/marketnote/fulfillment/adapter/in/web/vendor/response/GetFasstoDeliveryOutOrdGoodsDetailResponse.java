package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.FasstoDeliveryOutOrdGoodsInfoResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoDeliveryOutOrdGoodsDetailResult;

import java.util.List;

public record GetFasstoDeliveryOutOrdGoodsDetailResponse(
        Integer dataCount,
        List<FasstoDeliveryOutOrdGoodsInfoResult> goodsByInvoice
) {
    public static GetFasstoDeliveryOutOrdGoodsDetailResponse from(GetFasstoDeliveryOutOrdGoodsDetailResult result) {
        return new GetFasstoDeliveryOutOrdGoodsDetailResponse(result.dataCount(), result.goodsByInvoice());
    }
}
