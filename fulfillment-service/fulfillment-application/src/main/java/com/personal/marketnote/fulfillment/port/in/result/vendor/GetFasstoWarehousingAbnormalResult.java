package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record GetFasstoWarehousingAbnormalResult(
        Integer dataCount,
        List<FasstoWarehousingAbnormalInfoResult> abnormals
) {
    public static GetFasstoWarehousingAbnormalResult of(
            Integer dataCount,
            List<FasstoWarehousingAbnormalInfoResult> abnormals
    ) {
        return new GetFasstoWarehousingAbnormalResult(dataCount, abnormals);
    }
}
