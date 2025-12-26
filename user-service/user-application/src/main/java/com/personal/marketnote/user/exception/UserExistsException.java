package com.personal.marketnote.user.exception;

import jakarta.persistence.EntityExistsException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserExistsException extends EntityExistsException {
    private static final String USER_EXISTS_EXCEPTION_MESSAGE = "이미 가입된 회원입니다. OIDC ID: %s";

    public UserExistsException(String oidcId) {
        super(String.format(USER_EXISTS_EXCEPTION_MESSAGE, oidcId));
    }
}
