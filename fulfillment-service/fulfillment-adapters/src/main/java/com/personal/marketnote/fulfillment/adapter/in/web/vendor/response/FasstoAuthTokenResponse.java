package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.FasstoAccessTokenResult;

public record FasstoAuthTokenResponse(
        FasstoAccessTokenResult tokenInfo
) {
    public static FasstoAuthTokenResponse from(FasstoAccessTokenResult tokenInfo) {
        return new FasstoAuthTokenResponse(tokenInfo);
    }
}
