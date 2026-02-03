package com.personal.marketnote.product.adapter.out.web.fulfillment.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateFasstoGoodsItemResponse(
        String fmsSlipNo,
        String orderNo,
        String msg,
        String code,
        Object outOfStockGoodsDetail
) {
    private static final String SUCCESS_CODE = "200";

    public boolean isSuccess() {
        return SUCCESS_CODE.equals(code);
    }
}
