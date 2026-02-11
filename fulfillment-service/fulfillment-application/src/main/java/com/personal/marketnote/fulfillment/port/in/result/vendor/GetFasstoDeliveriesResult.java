package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record GetFasstoDeliveriesResult(
        Integer dataCount,
        List<FasstoDeliveryInfoResult> deliveries
) {
    public static GetFasstoDeliveriesResult of(
            Integer dataCount,
            List<FasstoDeliveryInfoResult> deliveries
    ) {
        return new GetFasstoDeliveriesResult(dataCount, deliveries);
    }
}
