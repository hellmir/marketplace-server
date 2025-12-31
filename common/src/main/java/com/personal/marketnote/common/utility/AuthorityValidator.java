package com.personal.marketnote.common.utility;

import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

public class AuthorityValidator {
    public static boolean hasAdminRole(OAuth2AuthenticatedPrincipal principal) {
        return principal.getAuthorities().stream()
                .anyMatch(grantedAuthority -> Role.isAdmin(grantedAuthority.getAuthority()));
    }
}
