package com.personal.marketnote.fulfillment.port.in.result.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.FasstoAccessToken;

import java.time.LocalDateTime;

public record FasstoAccessTokenResult(
        String accessToken,
        LocalDateTime expiresAt
) {
    public static FasstoAccessTokenResult from(FasstoAccessToken fasstoAccessToken) {
        return new FasstoAccessTokenResult(fasstoAccessToken.getValue(), fasstoAccessToken.getExpiresAt());
    }
}
