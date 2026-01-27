package com.personal.marketnote.fulfillment.port.in.result.vendor;

public record RegisterFasstoShopResult(
        String msg,
        String code,
        String shopCd
) {
    public static RegisterFasstoShopResult of(String msg, String code, String shopCd) {
        return new RegisterFasstoShopResult(msg, code, shopCd);
    }
}
