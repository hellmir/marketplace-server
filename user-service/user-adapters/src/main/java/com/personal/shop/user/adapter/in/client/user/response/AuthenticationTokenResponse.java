package com.personal.shop.user.adapter.in.client.user.response;

public record AuthenticationTokenResponse(
        String accessToken,
        String refreshToken
) {
}

