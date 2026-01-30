package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record GetFasstoGoodsResult(
        Integer dataCount,
        List<FasstoGoodsInfoResult> goods
) {
    public static GetFasstoGoodsResult of(
            Integer dataCount,
            List<FasstoGoodsInfoResult> goods
    ) {
        return new GetFasstoGoodsResult(dataCount, goods);
    }
}
