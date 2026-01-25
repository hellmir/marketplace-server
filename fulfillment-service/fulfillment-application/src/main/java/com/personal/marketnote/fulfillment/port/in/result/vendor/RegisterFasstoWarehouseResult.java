package com.personal.marketnote.fulfillment.port.in.result.vendor;

public record RegisterFasstoWarehouseResult(
        String msg,
        String code,
        String shopCd
) {
    public static RegisterFasstoWarehouseResult of(String msg, String code, String shopCd) {
        return new RegisterFasstoWarehouseResult(msg, code, shopCd);
    }
}
