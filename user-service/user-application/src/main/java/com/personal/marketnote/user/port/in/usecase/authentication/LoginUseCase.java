package com.personal.marketnote.user.port.in.usecase.authentication;

import com.personal.marketnote.user.port.in.result.LoginResult;
import com.personal.marketnote.user.security.token.exception.UnsupportedCodeException;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;

public interface LoginUseCase {
    LoginResult loginByOAuth2(String code, String redirectUri, AuthVendor authVendor) throws UnsupportedCodeException;
}
