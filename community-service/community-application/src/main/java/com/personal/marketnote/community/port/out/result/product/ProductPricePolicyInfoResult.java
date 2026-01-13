package com.personal.marketnote.community.port.out.result.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductPricePolicyInfoResult(
        Long id
) {
}
