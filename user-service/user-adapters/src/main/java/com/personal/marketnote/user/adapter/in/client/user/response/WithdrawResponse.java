package com.personal.marketnote.user.adapter.in.client.user.response;

import com.personal.marketnote.user.port.in.result.WithdrawResult;

public record WithdrawResponse(
        boolean isKakaoDisconnected,
        boolean isGoogleDisconnected,
        boolean isAppleDisconnected
) {
    public static WithdrawResponse from(WithdrawResult result) {
        return new WithdrawResponse(
                result.isKakaoDisconnected(),
                result.isGoogleDisconnected(),
                result.isAppleDisconnected()
        );
    }
}


