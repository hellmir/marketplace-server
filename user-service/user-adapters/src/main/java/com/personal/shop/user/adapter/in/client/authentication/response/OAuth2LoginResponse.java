package com.personal.shop.user.adapter.in.client.authentication.response;

import org.springframework.http.HttpHeaders;

public record OAuth2LoginResponse(
        HttpHeaders headers
) {
}
