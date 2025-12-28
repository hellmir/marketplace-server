package com.personal.marketnote.user.adapter.in.client.user.response;

public record SignOutResponse(
        String accessToken,
        String refreshToken
) {
    public static SignOutResponse of(String accessToken, String refreshToken) {
        return new SignOutResponse(accessToken, refreshToken);
    }
}
