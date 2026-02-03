package com.personal.marketnote.product.adapter.out.web.fulfillment.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FasstoGoodsElementItemResponse(
        String godCd,
        String cstGodCd,
        String godBarcd,
        String godNm,
        String godType,
        String godTypeNm,
        Integer qty
) {
}
