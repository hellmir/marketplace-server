package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.FasstoGoodsElementInfoResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoGoodsElementsResult;

import java.util.List;

public record GetFasstoGoodsElementsResponse(
        Integer dataCount,
        List<FasstoGoodsElementInfoResult> elements
) {
    public static GetFasstoGoodsElementsResponse from(GetFasstoGoodsElementsResult result) {
        return new GetFasstoGoodsElementsResponse(result.dataCount(), result.elements());
    }
}
