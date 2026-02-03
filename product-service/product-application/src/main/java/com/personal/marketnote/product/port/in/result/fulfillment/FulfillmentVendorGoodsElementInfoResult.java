package com.personal.marketnote.product.port.in.result.fulfillment;

import java.util.List;

public record FulfillmentVendorGoodsElementInfoResult(
        String godCd,
        String cstGodCd,
        String godNm,
        String useYn,
        List<FulfillmentVendorGoodsElementItemResult> elementList
) {
    public static FulfillmentVendorGoodsElementInfoResult of(
            String godCd,
            String cstGodCd,
            String godNm,
            String useYn,
            List<FulfillmentVendorGoodsElementItemResult> elementList
    ) {
        return new FulfillmentVendorGoodsElementInfoResult(
                godCd,
                cstGodCd,
                godNm,
                useYn,
                elementList
        );
    }
}
