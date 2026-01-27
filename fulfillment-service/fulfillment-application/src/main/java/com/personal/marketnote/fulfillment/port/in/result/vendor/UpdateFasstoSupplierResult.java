package com.personal.marketnote.fulfillment.port.in.result.vendor;

public record UpdateFasstoSupplierResult(
        String msg,
        String code,
        String supCd
) {
    public static UpdateFasstoSupplierResult of(String msg, String code, String supCd) {
        return new UpdateFasstoSupplierResult(msg, code, supCd);
    }
}
