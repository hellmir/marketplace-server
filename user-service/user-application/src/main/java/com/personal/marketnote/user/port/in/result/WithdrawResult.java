package com.personal.marketnote.user.port.in.result;

public record WithdrawResult(
        boolean isKakaoDisconnected,
        boolean isGoogleDisconnected,
        boolean isAppleDisconnected
) {
}


