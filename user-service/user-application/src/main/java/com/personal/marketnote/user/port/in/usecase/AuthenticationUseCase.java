package com.personal.marketnote.user.port.in.usecase;

import com.personal.marketnote.common.domain.exception.token.UnsupportedCodeException;
import com.personal.marketnote.user.port.in.result.GetUserResult;
import com.personal.marketnote.user.port.in.result.LoginResult;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;

import java.time.LocalDateTime;
import java.util.List;

public interface AuthenticationUseCase {
    LoginResult loginByOAuth2(String code, String redirectUri, AuthVendor authVendor) throws UnsupportedCodeException;

    List<LocalDateTime> getRecentLoginTimes(Long userId, int fetchCount);

    GetUserResult getUser(Long userId);
}
