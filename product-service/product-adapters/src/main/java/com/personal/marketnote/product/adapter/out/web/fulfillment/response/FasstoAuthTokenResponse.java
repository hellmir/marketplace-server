package com.personal.marketnote.product.adapter.out.web.fulfillment.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FasstoAuthTokenResponse(
        FasstoAccessTokenInfo tokenInfo
) {
}
