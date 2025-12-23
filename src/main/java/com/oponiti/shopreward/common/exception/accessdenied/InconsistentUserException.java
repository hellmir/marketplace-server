package com.oponiti.shopreward.common.exception.accessdenied;

public class InconsistentUserException extends IllegalArgumentException {
    public InconsistentUserException(String message) {
        super(message);
    }
}
