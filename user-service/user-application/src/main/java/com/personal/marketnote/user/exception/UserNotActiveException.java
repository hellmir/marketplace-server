package com.personal.marketnote.user.exception;

import lombok.Getter;
import org.springframework.security.access.AccessDeniedException;

@Getter
public class UserNotActiveException extends AccessDeniedException {
    private static final String USER_NOT_ACTIVE_EXCEPTION_MESSAGE = "%s:: 비활성화된 계정입니다. 전송된 이메일 주소: %s";

    public UserNotActiveException(String code, String email) {
        super(String.format(USER_NOT_ACTIVE_EXCEPTION_MESSAGE, code, email));
    }
}
