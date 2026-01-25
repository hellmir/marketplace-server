package com.personal.marketnote.fulfillment.port.in.result.vendor;

public record UpdateFasstoWarehouseResult(
        String msg,
        String code,
        String shopCd
) {
    public static UpdateFasstoWarehouseResult of(String msg, String code, String shopCd) {
        return new UpdateFasstoWarehouseResult(msg, code, shopCd);
    }
}
