package com.personal.shop.common.domain.exception.illegalargument.invalidvalue;

public abstract class InvalidValueException extends IllegalArgumentException {
    public InvalidValueException(String message) {
        super(message);
    }
}
