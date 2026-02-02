package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record FasstoGoodsElementInfoResult(
        String godCd,
        String cstGodCd,
        String godNm,
        String useYn,
        List<FasstoGoodsElementItemResult> elementList
) {
    public static FasstoGoodsElementInfoResult of(
            String godCd,
            String cstGodCd,
            String godNm,
            String useYn,
            List<FasstoGoodsElementItemResult> elementList
    ) {
        return new FasstoGoodsElementInfoResult(
                godCd,
                cstGodCd,
                godNm,
                useYn,
                elementList
        );
    }
}
