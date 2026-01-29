package com.personal.marketnote.product.adapter.out.web.fulfillment.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RegisterFasstoGoodsItemResponse(
        String msg,
        String code,
        String cstGodCd
) {
    private static final String SUCCESS_CODE = "200";

    public boolean isSuccess() {
        return SUCCESS_CODE.equals(code);
    }
}
