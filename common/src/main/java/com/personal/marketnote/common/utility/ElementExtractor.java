package com.personal.marketnote.common.utility;

import com.personal.marketnote.common.domain.exception.token.InvalidAccessTokenException;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import static com.personal.marketnote.common.domain.exception.ExceptionMessage.INVALID_ACCESS_TOKEN_EXCEPTION_MESSAGE;

public class ElementExtractor {
    public static Long extractUserId(OAuth2AuthenticatedPrincipal principal) {
        if (!FormatValidator.hasValue(principal) || FormatValidator.equals(principal.getName(), "-1")) {
            throw new InvalidAccessTokenException(INVALID_ACCESS_TOKEN_EXCEPTION_MESSAGE);
        }

        return FormatConverter.parseId(principal.getName());
    }
}
