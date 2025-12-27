package com.personal.marketnote.user.exception;

import jakarta.persistence.EntityExistsException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserExistsException extends EntityExistsException {
    public UserExistsException(String message) {
        super(message);
    }
}
