package com.personal.marketnote.user.adapter.in.client.user.response;

public record LogoutResponse(
        String accessToken,
        String refreshToken
) {
    public static LogoutResponse of(String accessToken, String refreshToken) {
        return new LogoutResponse(accessToken, refreshToken);
    }
}
