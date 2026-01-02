package com.personal.marketnote.user.port.out.authentication;

public interface ParseRefreshTokenPort {
    Long extractUserId(String refreshToken);
}


