package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoShopResult;

public record RegisterFasstoShopResponse(
        RegisterFasstoShopResult shopInfo
) {
    public static RegisterFasstoShopResponse from(RegisterFasstoShopResult shopInfo) {
        return new RegisterFasstoShopResponse(shopInfo);
    }
}
