package com.personal.marketnote.user.port.out.authentication;

public interface DeleteRefreshTokenPort {
    void deleteByUserId(Long userId);
}


