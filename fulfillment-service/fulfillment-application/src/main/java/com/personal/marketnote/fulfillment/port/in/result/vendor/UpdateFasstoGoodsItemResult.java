package com.personal.marketnote.fulfillment.port.in.result.vendor;

public record UpdateFasstoGoodsItemResult(
        String fmsSlipNo,
        String orderNo,
        String msg,
        String code,
        Object outOfStockGoodsDetail
) {
    public static UpdateFasstoGoodsItemResult of(
            String fmsSlipNo,
            String orderNo,
            String msg,
            String code,
            Object outOfStockGoodsDetail
    ) {
        return new UpdateFasstoGoodsItemResult(
                fmsSlipNo,
                orderNo,
                msg,
                code,
                outOfStockGoodsDetail
        );
    }
}
