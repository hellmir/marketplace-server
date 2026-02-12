package com.personal.marketnote.fulfillment.adapter.out.vendor.fassto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FasstoOutOrdGoodsDeliveredResponse(
        String cstGodCd,
        String godNm,
        Integer packQty
) {
}
