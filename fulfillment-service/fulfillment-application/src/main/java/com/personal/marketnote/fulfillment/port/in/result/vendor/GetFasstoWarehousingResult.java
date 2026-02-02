package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record GetFasstoWarehousingResult(
        Integer dataCount,
        List<FasstoWarehousingInfoResult> warehousing
) {
    public static GetFasstoWarehousingResult of(
            Integer dataCount,
            List<FasstoWarehousingInfoResult> warehousing
    ) {
        return new GetFasstoWarehousingResult(dataCount, warehousing);
    }
}
