package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record GetFasstoDeliveryDetailResult(
        Integer dataCount,
        List<FasstoDeliveryDetailInfoResult> deliveries
) {
    public static GetFasstoDeliveryDetailResult of(
            Integer dataCount,
            List<FasstoDeliveryDetailInfoResult> deliveries
    ) {
        return new GetFasstoDeliveryDetailResult(dataCount, deliveries);
    }
}
