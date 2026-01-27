package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.FasstoShopInfoResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoShopsResult;

import java.util.List;

public record GetFasstoShopsResponse(
        Integer dataCount,
        List<FasstoShopInfoResult> shops
) {
    public static GetFasstoShopsResponse from(GetFasstoShopsResult result) {
        return new GetFasstoShopsResponse(result.dataCount(), result.shops());
    }
}
