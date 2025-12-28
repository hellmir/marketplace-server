package com.personal.marketnote.user.exception;

import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

@Getter
public class UserOauth2VendorNotFoundException extends EntityNotFoundException {
    private static final String USER_OAUTH2_VENDOR_NOT_FOUND_EXCEPTION_MESSAGE = "%s:: 존재하지 않는 회원 OAuth2 인증 제공자입니다. 전송된 회원 OAuth2 인증 제공자: %s";

    public UserOauth2VendorNotFoundException(String code, AuthVendor authVendor) {
        super(String.format(USER_OAUTH2_VENDOR_NOT_FOUND_EXCEPTION_MESSAGE, code, authVendor));
    }
}
