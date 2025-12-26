package com.personal.shop.user.utility.jwt;

public enum JwtTokenType {
    ACCESS_TOKEN,
    REFRESH_TOKEN;

    public static JwtTokenType from(String name) {
        return JwtTokenType.valueOf(name.trim().toUpperCase());
    }
}
