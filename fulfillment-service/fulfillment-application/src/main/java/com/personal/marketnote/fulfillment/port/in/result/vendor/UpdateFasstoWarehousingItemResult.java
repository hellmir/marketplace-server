package com.personal.marketnote.fulfillment.port.in.result.vendor;

public record UpdateFasstoWarehousingItemResult(
        String msg,
        String code,
        String slipNo,
        String ordNo
) {
    public static UpdateFasstoWarehousingItemResult of(
            String msg,
            String code,
            String slipNo,
            String ordNo
    ) {
        return new UpdateFasstoWarehousingItemResult(msg, code, slipNo, ordNo);
    }
}
