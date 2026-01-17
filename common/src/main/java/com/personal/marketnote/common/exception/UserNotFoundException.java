package com.personal.marketnote.common.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

@Getter
public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
