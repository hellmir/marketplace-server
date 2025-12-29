package com.personal.marketnote.user.adapter.in.client.user.response;

public record SignUpResponse(
        String accessToken,
        String refreshToken,
        boolean isNewUser
) {
    public static SignUpResponse of(String accessToken, String refreshToken, boolean isNewUser) {
        return new SignUpResponse(accessToken, refreshToken, isNewUser);
    }
}
