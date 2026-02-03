package com.personal.marketnote.product.adapter.out.web.fulfillment.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.personal.marketnote.common.utility.FormatValidator;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateFasstoGoodsResponse(
        Integer dataCount,
        List<UpdateFasstoGoodsItemResponse> goods
) {
    public boolean isSuccess() {
        return FormatValidator.hasValue(goods)
                && goods.stream().allMatch(UpdateFasstoGoodsItemResponse::isSuccess);
    }
}
