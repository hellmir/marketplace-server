package com.personal.marketnote.fulfillment.port.in.result.vendor;

public record RegisterFasstoWarehousingItemResult(
        String msg,
        String code,
        String slipNo,
        String ordNo
) {
    public static RegisterFasstoWarehousingItemResult of(
            String msg,
            String code,
            String slipNo,
            String ordNo
    ) {
        return new RegisterFasstoWarehousingItemResult(msg, code, slipNo, ordNo);
    }
}
