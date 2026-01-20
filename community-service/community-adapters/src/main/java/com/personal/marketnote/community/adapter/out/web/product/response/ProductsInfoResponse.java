package com.personal.marketnote.community.adapter.out.web.product.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.personal.marketnote.community.port.out.result.product.ProductOptionInfoResult;
import com.personal.marketnote.community.port.out.result.product.ProductPricePolicyInfoResult;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductsInfoResponse(
        Long sellerId,
        String name,
        String brandName,
        ProductPricePolicyInfoResult pricePolicy,
        List<ProductOptionInfoResult> selectedOptions
) {
    public Long getPricePolicyId() {
        return pricePolicy.id();
    }
}
