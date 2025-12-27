package com.personal.marketnote.user.security.token.introspector;

import com.personal.marketnote.user.constant.PrimaryRole;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.port.out.FindUserPort;
import com.personal.marketnote.user.security.token.dto.OAuth2AuthenticationInfo;
import com.personal.marketnote.user.security.token.exception.InvalidAccessTokenException;
import com.personal.marketnote.user.security.token.support.TokenSupport;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class OpaqueTokenDefaultIntrospector implements OpaqueTokenIntrospector {
    private final TokenSupport tokenSupport;
    private final FindUserPort findUserPort;

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        try {
            OAuth2AuthenticationInfo userInfo = tokenSupport.authenticate(token);
            String oidcId = userInfo.id();
            Optional<User> user = findUserPort.findByOidcId(oidcId);

            if (user.isPresent()) {
                User signedUpUser = user.get();

                return new DefaultOAuth2AuthenticatedPrincipal(
                        String.valueOf(signedUpUser.getId()),
                        Map.of(
                                "sub", oidcId == null ? "" : oidcId,
                                "iss", userInfo.authVendor().name()
                        ),
                        List.of(new SimpleGrantedAuthority(signedUpUser.getRole().getId()))
                );
            }

            return new DefaultOAuth2AuthenticatedPrincipal(
                    "-1",
                    Map.of(
                            "sub", oidcId == null ? "" : oidcId,
                            "iss", userInfo.authVendor().name()
                    ),
                    List.of(new SimpleGrantedAuthority(PrimaryRole.ROLE_GUEST.name()))
            );
        } catch (InvalidAccessTokenException e) {
            return new DefaultOAuth2AuthenticatedPrincipal(
                    "-1",
                    Map.of("sub", "",
                            "iss", AuthVendor.NATIVE.name()),
                    List.of(new SimpleGrantedAuthority(PrimaryRole.ROLE_ANONYMOUS.name()))
            );
        }
    }
}
