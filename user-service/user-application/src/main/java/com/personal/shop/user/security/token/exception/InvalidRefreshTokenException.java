package com.personal.shop.user.security.token.exception;

public class InvalidRefreshTokenException extends TokenException {

    public InvalidRefreshTokenException(String message) {
        super(message);
    }

    public InvalidRefreshTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
