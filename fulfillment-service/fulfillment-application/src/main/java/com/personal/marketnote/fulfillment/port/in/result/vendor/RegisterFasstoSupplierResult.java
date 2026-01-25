package com.personal.marketnote.fulfillment.port.in.result.vendor;

public record RegisterFasstoSupplierResult(
        String msg,
        String code,
        String supCd
) {
    public static RegisterFasstoSupplierResult of(String msg, String code, String supCd) {
        return new RegisterFasstoSupplierResult(msg, code, supCd);
    }
}
