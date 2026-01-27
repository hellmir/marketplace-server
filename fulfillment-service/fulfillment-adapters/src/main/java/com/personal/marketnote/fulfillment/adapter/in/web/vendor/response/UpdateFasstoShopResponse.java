package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.UpdateFasstoShopResult;

public record UpdateFasstoShopResponse(
        UpdateFasstoShopResult shopInfo
) {
    public static UpdateFasstoShopResponse from(UpdateFasstoShopResult shopInfo) {
        return new UpdateFasstoShopResponse(shopInfo);
    }
}
