package com.personal.marketnote.user.adapter.in.client.user.response;

public record AuthenticationTokenResponse(
        String accessToken,
        String refreshToken
) {
}

