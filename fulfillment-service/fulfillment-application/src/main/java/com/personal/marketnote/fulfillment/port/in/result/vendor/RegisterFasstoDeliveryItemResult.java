package com.personal.marketnote.fulfillment.port.in.result.vendor;

public record RegisterFasstoDeliveryItemResult(
        String msg,
        String code,
        String slipNo,
        String ordNo
) {
    public static RegisterFasstoDeliveryItemResult of(
            String msg,
            String code,
            String slipNo,
            String ordNo
    ) {
        return new RegisterFasstoDeliveryItemResult(msg, code, slipNo, ordNo);
    }
}
