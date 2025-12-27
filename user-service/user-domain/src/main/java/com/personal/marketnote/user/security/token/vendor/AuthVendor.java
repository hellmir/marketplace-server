package com.personal.marketnote.user.security.token.vendor;

/**
 * OAuth 2.0 Authorization Server 목록을 정리한 enum.
 *
 * @author 성효빈
 */
public enum AuthVendor {
    KAKAO,
    GOOGLE,
    APPLE,
    NATIVE;

    public static AuthVendor valueOfIgnoreCase(String value) throws IllegalArgumentException {
        return valueOf(value.toUpperCase());
    }
}
