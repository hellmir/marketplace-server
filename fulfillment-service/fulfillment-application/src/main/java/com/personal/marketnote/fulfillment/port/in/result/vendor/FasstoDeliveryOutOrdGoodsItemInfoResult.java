package com.personal.marketnote.fulfillment.port.in.result.vendor;

public record FasstoDeliveryOutOrdGoodsItemInfoResult(
        String cstGodCd,
        String godNm,
        Integer packQty
) {
    public static FasstoDeliveryOutOrdGoodsItemInfoResult of(
            String cstGodCd,
            String godNm,
            Integer packQty
    ) {
        return new FasstoDeliveryOutOrdGoodsItemInfoResult(cstGodCd, godNm, packQty);
    }
}
