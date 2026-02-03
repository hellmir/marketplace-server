package com.personal.marketnote.product.port.in.result.fulfillment;

public record FulfillmentVendorGoodsElementItemResult(
        String godCd,
        String cstGodCd,
        String godBarcd,
        String godNm,
        String godType,
        String godTypeNm,
        Integer qty
) {
    public static FulfillmentVendorGoodsElementItemResult of(
            String godCd,
            String cstGodCd,
            String godBarcd,
            String godNm,
            String godType,
            String godTypeNm,
            Integer qty
    ) {
        return new FulfillmentVendorGoodsElementItemResult(
                godCd,
                cstGodCd,
                godBarcd,
                godNm,
                godType,
                godTypeNm,
                qty
        );
    }
}
