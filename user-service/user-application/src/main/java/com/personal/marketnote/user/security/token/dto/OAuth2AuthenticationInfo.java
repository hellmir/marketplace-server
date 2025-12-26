package com.personal.marketnote.user.security.token.dto;

import lombok.Builder;

@Builder
public record OAuth2AuthenticationInfo(
        String id
) {
}
