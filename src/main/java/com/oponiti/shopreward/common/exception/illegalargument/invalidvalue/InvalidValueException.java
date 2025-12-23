package com.oponiti.shopreward.common.exception.illegalargument.invalidvalue;

public abstract class InvalidValueException extends IllegalArgumentException {
    public InvalidValueException(String message) {
        super(message);
    }
}
