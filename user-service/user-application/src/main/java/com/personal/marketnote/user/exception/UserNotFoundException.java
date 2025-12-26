package com.personal.marketnote.user.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

@Getter
public class UserNotFoundException extends EntityNotFoundException {
    private static final String USER_NOT_FOUND_EXCEPTION_MESSAGE
            = "존재하지 않는 회원입니다. 회원 ID: %d";

    public UserNotFoundException(Long userId) {
        super(String.format(USER_NOT_FOUND_EXCEPTION_MESSAGE, userId));
    }
}
