package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record UpdateFasstoWarehousingResult(
        Integer dataCount,
        List<UpdateFasstoWarehousingItemResult> warehousing
) {
    public static UpdateFasstoWarehousingResult of(
            Integer dataCount,
            List<UpdateFasstoWarehousingItemResult> warehousing
    ) {
        return new UpdateFasstoWarehousingResult(dataCount, warehousing);
    }
}
