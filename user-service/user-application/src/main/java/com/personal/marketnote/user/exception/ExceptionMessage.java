package com.personal.marketnote.user.exception;

import jakarta.persistence.EntityExistsException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ExceptionMessage extends EntityExistsException {
    public static final String OIDC_ID_EXISTS_EXCEPTION_MESSAGE = "이미 가입된 회원입니다. 가입된 OIDC ID: %s";
    public static final String PHONE_NUMBER_EXISTS_EXCEPTION_MESSAGE = "이미 가입된 회원입니다. 가입된 전화번호: %s";
}
