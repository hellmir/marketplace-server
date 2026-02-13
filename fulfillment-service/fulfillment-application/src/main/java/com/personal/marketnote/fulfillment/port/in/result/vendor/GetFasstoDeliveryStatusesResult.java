package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record GetFasstoDeliveryStatusesResult(
        Integer dataCount,
        List<FasstoDeliveryStatusInfoResult> deliveryStatuses
) {
    public static GetFasstoDeliveryStatusesResult of(
            Integer dataCount,
            List<FasstoDeliveryStatusInfoResult> deliveryStatuses
    ) {
        return new GetFasstoDeliveryStatusesResult(dataCount, deliveryStatuses);
    }
}
