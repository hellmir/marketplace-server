package com.personal.shop.common.domain.exception.accessdenied;

public class InconsistentUserException extends IllegalArgumentException {
    public InconsistentUserException(String message) {
        super(message);
    }
}
