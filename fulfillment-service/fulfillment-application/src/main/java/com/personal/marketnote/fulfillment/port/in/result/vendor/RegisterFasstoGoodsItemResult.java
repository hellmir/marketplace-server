package com.personal.marketnote.fulfillment.port.in.result.vendor;

public record RegisterFasstoGoodsItemResult(
        String msg,
        String code,
        String cstGodCd
) {
    public static RegisterFasstoGoodsItemResult of(String msg, String code, String cstGodCd) {
        return new RegisterFasstoGoodsItemResult(msg, code, cstGodCd);
    }
}
