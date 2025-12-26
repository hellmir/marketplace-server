package com.personal.marketnote.user.security.token.exception;

import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;

/**
 * 토큰과 관련된 예외 클래스의 base class.
 */
public abstract class TokenException extends InvalidBearerTokenException {

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
