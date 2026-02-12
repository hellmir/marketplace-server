package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record CancelFasstoDeliveryResult(
        Integer dataCount,
        List<CancelFasstoDeliveryItemResult> deliveries
) {
    public static CancelFasstoDeliveryResult of(
            Integer dataCount,
            List<CancelFasstoDeliveryItemResult> deliveries
    ) {
        return new CancelFasstoDeliveryResult(dataCount, deliveries);
    }
}
