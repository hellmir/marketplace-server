package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record CancelFasstoDeliveryItemCommand(
        String slipNo,
        String ordNo
) {
    public static CancelFasstoDeliveryItemCommand of(
            String slipNo,
            String ordNo
    ) {
        return new CancelFasstoDeliveryItemCommand(slipNo, ordNo);
    }
}
