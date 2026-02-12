package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record FasstoDeliveryOutOrdGoodsInfoResult(
        String invoiceNo,
        List<FasstoDeliveryOutOrdGoodsItemInfoResult> goodsDeliveredList
) {
    public static FasstoDeliveryOutOrdGoodsInfoResult of(
            String invoiceNo,
            List<FasstoDeliveryOutOrdGoodsItemInfoResult> goodsDeliveredList
    ) {
        return new FasstoDeliveryOutOrdGoodsInfoResult(invoiceNo, goodsDeliveredList);
    }
}
