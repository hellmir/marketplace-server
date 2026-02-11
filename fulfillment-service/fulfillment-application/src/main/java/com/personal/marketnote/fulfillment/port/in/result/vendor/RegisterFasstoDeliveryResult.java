package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record RegisterFasstoDeliveryResult(
        Integer dataCount,
        List<RegisterFasstoDeliveryItemResult> deliveries
) {
    public static RegisterFasstoDeliveryResult of(
            Integer dataCount,
            List<RegisterFasstoDeliveryItemResult> deliveries
    ) {
        return new RegisterFasstoDeliveryResult(dataCount, deliveries);
    }
}
