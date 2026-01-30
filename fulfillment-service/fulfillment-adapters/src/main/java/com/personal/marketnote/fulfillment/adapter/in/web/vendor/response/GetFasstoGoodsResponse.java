package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.FasstoGoodsInfoResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoGoodsResult;

import java.util.List;

public record GetFasstoGoodsResponse(
        Integer dataCount,
        List<FasstoGoodsInfoResult> goods
) {
    public static GetFasstoGoodsResponse from(GetFasstoGoodsResult result) {
        return new GetFasstoGoodsResponse(result.dataCount(), result.goods());
    }
}
