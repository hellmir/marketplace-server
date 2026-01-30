package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record UpdateFasstoGoodsResult(
        Integer dataCount,
        List<UpdateFasstoGoodsItemResult> goods
) {
    public static UpdateFasstoGoodsResult of(
            Integer dataCount,
            List<UpdateFasstoGoodsItemResult> goods
    ) {
        return new UpdateFasstoGoodsResult(dataCount, goods);
    }
}
