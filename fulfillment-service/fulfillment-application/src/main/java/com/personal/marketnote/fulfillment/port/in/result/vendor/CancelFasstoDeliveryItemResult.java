package com.personal.marketnote.fulfillment.port.in.result.vendor;

public record CancelFasstoDeliveryItemResult(
        String fmsSlipNo,
        String orderNo,
        String msg,
        String code,
        Object outOfStockGoodsDetail
) {
    public static CancelFasstoDeliveryItemResult of(
            String fmsSlipNo,
            String orderNo,
            String msg,
            String code,
            Object outOfStockGoodsDetail
    ) {
        return new CancelFasstoDeliveryItemResult(fmsSlipNo, orderNo, msg, code, outOfStockGoodsDetail);
    }
}
