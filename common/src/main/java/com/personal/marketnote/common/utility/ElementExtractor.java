package com.personal.marketnote.common.utility;

import com.personal.marketnote.common.domain.exception.illegalargument.invalidvalue.InvalidIdException;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

public class ElementExtractor {
    public static Long extractUserId(OAuth2AuthenticatedPrincipal principal) {
        if (!FormatValidator.hasValue(principal)) {
            throw new InvalidIdException(String.valueOf(principal));
        }

        return FormatConverter.parseId(principal.getName());
    }
}
