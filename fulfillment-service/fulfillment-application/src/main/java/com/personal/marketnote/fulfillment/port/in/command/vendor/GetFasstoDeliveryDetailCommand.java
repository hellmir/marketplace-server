package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record GetFasstoDeliveryDetailCommand(
        String customerCode,
        String accessToken,
        String slipNo,
        String ordNo
) {
    public static GetFasstoDeliveryDetailCommand of(
            String customerCode,
            String accessToken,
            String slipNo
    ) {
        return new GetFasstoDeliveryDetailCommand(customerCode, accessToken, slipNo, null);
    }

    public static GetFasstoDeliveryDetailCommand of(
            String customerCode,
            String accessToken,
            String slipNo,
            String ordNo
    ) {
        return new GetFasstoDeliveryDetailCommand(customerCode, accessToken, slipNo, ordNo);
    }
}
