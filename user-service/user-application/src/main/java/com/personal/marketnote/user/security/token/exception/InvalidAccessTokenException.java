package com.personal.marketnote.user.security.token.exception;

public class InvalidAccessTokenException extends TokenException {
    public InvalidAccessTokenException(String message) {
        super(message);
    }

    public InvalidAccessTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
