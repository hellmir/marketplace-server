package com.oponiti.shopreward.common.exception.novalue;

public abstract class NoValueException extends IllegalArgumentException {
    public NoValueException(String message) {
        super(message);
    }
}
