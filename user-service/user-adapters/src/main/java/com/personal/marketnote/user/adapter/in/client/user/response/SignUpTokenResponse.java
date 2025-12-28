package com.personal.marketnote.user.adapter.in.client.user.response;

public record SignUpTokenResponse(
        String accessToken,
        String refreshToken,
        boolean isNewUser
) {
    public static SignUpTokenResponse of(String accessToken, String refreshToken, boolean isNewUser) {
        return new SignUpTokenResponse(accessToken, refreshToken, isNewUser);
    }
}
