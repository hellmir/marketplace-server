package com.personal.marketnote.fulfillment.port.in.command.vendor;

import java.util.List;

public record RegisterFasstoDeliveryCommand(
        String customerCode,
        String accessToken,
        List<RegisterFasstoDeliveryItemCommand> deliveryRequests
) {
    public static RegisterFasstoDeliveryCommand of(
            String customerCode,
            String accessToken,
            List<RegisterFasstoDeliveryItemCommand> deliveryRequests
    ) {
        return new RegisterFasstoDeliveryCommand(customerCode, accessToken, deliveryRequests);
    }
}
