package com.personal.marketnote.fulfillment.port.in.command.vendor;

import java.util.List;

public record CancelFasstoDeliveryCommand(
        String customerCode,
        String accessToken,
        List<CancelFasstoDeliveryItemCommand> deliveries
) {
    public static CancelFasstoDeliveryCommand of(
            String customerCode,
            String accessToken,
            List<CancelFasstoDeliveryItemCommand> deliveries
    ) {
        return new CancelFasstoDeliveryCommand(customerCode, accessToken, deliveries);
    }
}
