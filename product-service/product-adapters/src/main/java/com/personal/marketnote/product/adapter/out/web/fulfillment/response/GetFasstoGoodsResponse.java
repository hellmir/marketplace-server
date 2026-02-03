package com.personal.marketnote.product.adapter.out.web.fulfillment.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetFasstoGoodsResponse(
        Integer dataCount,
        List<FasstoGoodsItemResponse> goods
) {
    public boolean isSuccess() {
        return goods != null;
    }
}
