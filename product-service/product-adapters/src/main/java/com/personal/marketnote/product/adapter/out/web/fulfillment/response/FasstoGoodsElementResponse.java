package com.personal.marketnote.product.adapter.out.web.fulfillment.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FasstoGoodsElementResponse(
        String godCd,
        String cstGodCd,
        String godNm,
        String useYn,
        List<FasstoGoodsElementItemResponse> elementList
) {
}
