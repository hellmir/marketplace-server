package com.personal.marketnote.user.adapter.in.client.authentication.response;

public record verifyCodeResponse(
        String accessToken,
        String refreshToken
) {
    public static verifyCodeResponse of(String accessToken, String refreshToken) {
        return new verifyCodeResponse(accessToken, refreshToken);
    }
}
