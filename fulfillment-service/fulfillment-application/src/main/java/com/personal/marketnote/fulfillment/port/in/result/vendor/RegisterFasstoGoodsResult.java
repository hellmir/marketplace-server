package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record RegisterFasstoGoodsResult(
        Integer dataCount,
        List<RegisterFasstoGoodsItemResult> goods
) {
    public static RegisterFasstoGoodsResult of(
            Integer dataCount,
            List<RegisterFasstoGoodsItemResult> goods
    ) {
        return new RegisterFasstoGoodsResult(dataCount, goods);
    }
}
