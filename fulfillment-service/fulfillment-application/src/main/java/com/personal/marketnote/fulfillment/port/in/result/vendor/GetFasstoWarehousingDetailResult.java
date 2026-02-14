package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record GetFasstoWarehousingDetailResult(
        Integer dataCount,
        List<FasstoWarehousingDetailInfoResult> details
) {
    public static GetFasstoWarehousingDetailResult of(
            Integer dataCount,
            List<FasstoWarehousingDetailInfoResult> details
    ) {
        return new GetFasstoWarehousingDetailResult(dataCount, details);
    }
}
