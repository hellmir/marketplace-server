package com.personal.marketnote.user.exception;

import jakarta.persistence.EntityExistsException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ExceptionMessage extends EntityExistsException {
    public static final String USER_ID_NOT_FOUND_EXCEPTION_MESSAGE = "존재하지 않는 회원입니다. 회원 ID: %d";
    public static final String USER_OIDC_ID_NOT_FOUND_EXCEPTION_MESSAGE = "존재하지 않는 회원입니다. 회원 OIDC ID: %s";
    public static final String USER_PHONE_NUMBER_NOT_FOUND_EXCEPTION_MESSAGE = "존재하지 않는 회원입니다. 회원 전화번호: %s";
    public static final String OIDC_ID_EXISTS_EXCEPTION_MESSAGE = "이미 가입된 회원입니다. 가입된 OIDC ID: %s";
    public static final String PHONE_NUMBER_EXISTS_EXCEPTION_MESSAGE = "이미 가입된 회원입니다. 가입된 전화번호: %s";
}
