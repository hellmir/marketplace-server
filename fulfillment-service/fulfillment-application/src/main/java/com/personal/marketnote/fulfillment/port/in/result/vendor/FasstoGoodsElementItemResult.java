package com.personal.marketnote.fulfillment.port.in.result.vendor;

public record FasstoGoodsElementItemResult(
        String godCd,
        String cstGodCd,
        String godBarcd,
        String godNm,
        String godType,
        String godTypeNm,
        Integer qty
) {
    public static FasstoGoodsElementItemResult of(
            String godCd,
            String cstGodCd,
            String godBarcd,
            String godNm,
            String godType,
            String godTypeNm,
            Integer qty
    ) {
        return new FasstoGoodsElementItemResult(
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
