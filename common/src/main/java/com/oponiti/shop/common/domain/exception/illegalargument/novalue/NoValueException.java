package com.oponiti.shop.common.domain.exception.illegalargument.novalue;

public abstract class NoValueException extends IllegalArgumentException {
    public NoValueException(String message) {
        super(message);
    }
}
