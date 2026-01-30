package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.UpdateFasstoGoodsItemResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.UpdateFasstoGoodsResult;

import java.util.List;

public record UpdateFasstoGoodsResponse(
        Integer dataCount,
        List<UpdateFasstoGoodsItemResult> goods
) {
    public static UpdateFasstoGoodsResponse from(UpdateFasstoGoodsResult result) {
        return new UpdateFasstoGoodsResponse(result.dataCount(), result.goods());
    }
}
