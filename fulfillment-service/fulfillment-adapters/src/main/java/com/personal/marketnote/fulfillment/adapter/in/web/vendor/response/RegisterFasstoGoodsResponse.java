package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoGoodsItemResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoGoodsResult;

import java.util.List;

public record RegisterFasstoGoodsResponse(
        Integer dataCount,
        List<RegisterFasstoGoodsItemResult> goods
) {
    public static RegisterFasstoGoodsResponse from(RegisterFasstoGoodsResult result) {
        return new RegisterFasstoGoodsResponse(result.dataCount(), result.goods());
    }
}
