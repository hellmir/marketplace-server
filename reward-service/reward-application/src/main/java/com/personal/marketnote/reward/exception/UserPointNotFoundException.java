package com.personal.marketnote.reward.exception;

import jakarta.persistence.EntityNotFoundException;

public class UserPointNotFoundException extends EntityNotFoundException {
    private static final String USER_POINT_NOT_FOUND_EXCEPTION_MESSAGE = "회원 포인트 정보를 찾을 수 없습니다. 전송된 회원 ID: %d";

    public UserPointNotFoundException(Long userId) {
        super(String.format(USER_POINT_NOT_FOUND_EXCEPTION_MESSAGE, userId));
    }
}
