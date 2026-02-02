package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record GetFasstoGoodsElementsResult(
        Integer dataCount,
        List<FasstoGoodsElementInfoResult> elements
) {
    public static GetFasstoGoodsElementsResult of(
            Integer dataCount,
            List<FasstoGoodsElementInfoResult> elements
    ) {
        return new GetFasstoGoodsElementsResult(dataCount, elements);
    }
}
