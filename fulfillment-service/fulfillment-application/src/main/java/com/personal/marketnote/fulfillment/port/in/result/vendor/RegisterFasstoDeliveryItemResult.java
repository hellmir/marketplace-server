package com.personal.marketnote.fulfillment.port.in.result.vendor;

public record RegisterFasstoDeliveryItemResult(
        String fmsSlipNo,
        String orderNo,
        String msg,
        String code,
        Object outOfStockGoodsDetail
) {
    public static RegisterFasstoDeliveryItemResult of(
            String fmsSlipNo,
            String orderNo,
            String msg,
            String code,
            Object outOfStockGoodsDetail
    ) {
        return new RegisterFasstoDeliveryItemResult(
                fmsSlipNo,
                orderNo,
                msg,
                code,
                outOfStockGoodsDetail
        );
    }
}
