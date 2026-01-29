package com.personal.marketnote.product.adapter.out.web.fulfillment.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FasstoAccessTokenInfo(
        String accessToken,
        LocalDateTime expiresAt
) {
}
