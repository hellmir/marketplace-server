package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record RegisterFasstoWarehousingResult(
        Integer dataCount,
        List<RegisterFasstoWarehousingItemResult> warehousing
) {
    public static RegisterFasstoWarehousingResult of(
            Integer dataCount,
            List<RegisterFasstoWarehousingItemResult> warehousing
    ) {
        return new RegisterFasstoWarehousingResult(dataCount, warehousing);
    }
}
