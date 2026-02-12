package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record GetFasstoDeliveryOutOrdGoodsDetailResult(
        Integer dataCount,
        List<FasstoDeliveryOutOrdGoodsInfoResult> goodsByInvoice
) {
    public static GetFasstoDeliveryOutOrdGoodsDetailResult of(
            Integer dataCount,
            List<FasstoDeliveryOutOrdGoodsInfoResult> goodsByInvoice
    ) {
        return new GetFasstoDeliveryOutOrdGoodsDetailResult(dataCount, goodsByInvoice);
    }
}
