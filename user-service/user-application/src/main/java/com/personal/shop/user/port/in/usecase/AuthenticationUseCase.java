package com.personal.shop.user.port.in.usecase;

import com.personal.shop.user.port.in.result.GetUserResult;
import com.personal.shop.user.port.in.result.LoginResult;
import com.personal.shop.user.security.token.exception.UnsupportedCodeException;
import com.personal.shop.user.security.token.vendor.AuthVendor;

import java.time.LocalDateTime;
import java.util.List;

public interface AuthenticationUseCase {
    LoginResult loginByOAuth2(String code, String redirectUri, AuthVendor authVendor) throws UnsupportedCodeException;

    List<LocalDateTime> getRecentLoginTimes(Long userId, int fetchCount);

    GetUserResult getUser(Long userId);
}
