package com.personal.marketnote.user.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

@Getter
public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
