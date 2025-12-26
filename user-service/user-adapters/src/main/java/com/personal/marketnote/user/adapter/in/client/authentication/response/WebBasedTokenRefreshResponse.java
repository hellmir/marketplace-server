package com.personal.marketnote.user.adapter.in.client.authentication.response;

import org.springframework.http.HttpHeaders;

public record WebBasedTokenRefreshResponse(
        HttpHeaders headers,
        RefreshedAccessTokenResponse accessToken
) {
}
