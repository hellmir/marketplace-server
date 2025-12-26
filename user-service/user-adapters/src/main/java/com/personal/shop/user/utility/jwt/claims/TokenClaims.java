package com.personal.shop.user.utility.jwt.claims;

import com.personal.shop.user.security.token.vendor.AuthVendor;
import com.personal.shop.user.utility.jwt.JwtTokenType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class TokenClaims {
    private final String id;
    private final Long userId;
    private final List<String> roleIds;
    private final LocalDateTime expirationAt;
    private final LocalDateTime issuedAt;
    private final JwtTokenType tokenType;
    private final AuthVendor authVendor;
}
