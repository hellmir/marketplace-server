package com.personal.shop.user.security.token.dto;

import lombok.Builder;

@Builder
public record OAuth2UserInfo(
        String id,
        String name,
        String profileImageUrl
) {
}
