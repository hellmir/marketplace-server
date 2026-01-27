package com.personal.marketnote.fulfillment.port.in.result.vendor;

public record UpdateFasstoShopResult(
        String msg,
        String code,
        String shopCd
) {
    public static UpdateFasstoShopResult of(String msg, String code, String shopCd) {
        return new UpdateFasstoShopResult(msg, code, shopCd);
    }
}
