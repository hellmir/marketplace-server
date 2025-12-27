package com.personal.marketnote.user.security.token.introspector;

import com.personal.marketnote.common.domain.exception.token.InvalidAccessTokenException;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.user.constant.PrimaryRole;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.port.out.user.FindUserPort;
import com.personal.marketnote.user.security.token.dto.OAuth2AuthenticationInfo;
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

import static com.personal.marketnote.user.security.token.utility.TokenConstant.ISS_CLAIM_KEY;
import static com.personal.marketnote.user.security.token.utility.TokenConstant.SUB_CLAIM_KEY;

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
            AuthVendor authVendor = userInfo.authVendor();
            User user = findUserPort.findByAuthVendorAndOidcId(authVendor, oidcId)
                    .orElse(
                            findUserPort.findById(userInfo.userId()).orElse(null)
                    );

            if (FormatValidator.hasValue(user)) {
                return new DefaultOAuth2AuthenticatedPrincipal(
                        String.valueOf(user.getId()),
                        Map.of(
                                SUB_CLAIM_KEY, !FormatValidator.hasValue(oidcId) ? "" : oidcId,
                                ISS_CLAIM_KEY, userInfo.authVendor().name()
                        ),
                        List.of(new SimpleGrantedAuthority(user.getRole().getId()))
                );
            }

            return new DefaultOAuth2AuthenticatedPrincipal(
                    "-1",
                    Map.of(
                            SUB_CLAIM_KEY, !FormatValidator.hasValue(oidcId) ? "" : oidcId,
                            ISS_CLAIM_KEY, userInfo.authVendor().name()
                    ),
                    List.of(new SimpleGrantedAuthority(PrimaryRole.ROLE_GUEST.name()))
            );
        } catch (InvalidAccessTokenException e) {
            return new DefaultOAuth2AuthenticatedPrincipal(
                    "-1",
                    Map.of(
                            SUB_CLAIM_KEY, "",
                            ISS_CLAIM_KEY, AuthVendor.NATIVE.name()
                    ),
                    List.of(new SimpleGrantedAuthority(PrimaryRole.ROLE_ANONYMOUS.name()))
            );
        }
    }
}
