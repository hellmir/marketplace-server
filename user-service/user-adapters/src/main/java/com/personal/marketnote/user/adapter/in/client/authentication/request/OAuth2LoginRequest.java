package com.personal.marketnote.user.adapter.in.client.authentication.request;

import jakarta.servlet.http.HttpServletRequest;

public record OAuth2LoginRequest(
        String authVendor,
        String code,
        String state,
        HttpServletRequest request
) {
}
