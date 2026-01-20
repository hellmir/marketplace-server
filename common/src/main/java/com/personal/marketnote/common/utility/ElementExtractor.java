package com.personal.marketnote.common.utility;

import com.personal.marketnote.common.domain.exception.token.AuthenticationFailedException;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import static com.personal.marketnote.common.domain.exception.ExceptionMessage.INVALID_ACCESS_TOKEN_EXCEPTION_MESSAGE;

public class ElementExtractor {
    public static Long extractUserId(OAuth2AuthenticatedPrincipal principal) {
        if (FormatValidator.hasNoValue(principal) || FormatValidator.equals(principal.getName(), "-1")) {
            throw new AuthenticationFailedException(INVALID_ACCESS_TOKEN_EXCEPTION_MESSAGE);
        }

        return FormatConverter.parseId(principal.getName());
    }
}
