package com.personal.marketnote.user.utility.jwt;

import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.port.out.FindUserPort;
import com.personal.marketnote.user.security.token.dto.GrantedTokenInfo;
import com.personal.marketnote.user.security.token.dto.OAuth2AuthenticationInfo;
import com.personal.marketnote.user.security.token.dto.OAuth2UserInfo;
import com.personal.marketnote.user.security.token.exception.InvalidAccessTokenException;
import com.personal.marketnote.user.security.token.exception.InvalidRefreshTokenException;
import com.personal.marketnote.user.security.token.exception.UnsupportedCodeException;
import com.personal.marketnote.user.security.token.support.DelegatingTokenSupport;
import com.personal.marketnote.user.security.token.support.TokenProcessor;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import com.personal.marketnote.user.utility.jwt.claims.TokenClaims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("qa.test | prod")
@Primary
@Slf4j
public class JwtDelegatingTokenSupportProxy extends DelegatingTokenSupport {
    private final JwtUtil jwtUtil;
    private final FindUserPort findUserPort;

    public JwtDelegatingTokenSupportProxy(
            List<TokenProcessor> processors, JwtUtil jwtUtil, FindUserPort findUserPort
    ) {
        super(processors);
        this.jwtUtil = jwtUtil;
        this.findUserPort = findUserPort;
    }

    @Override
    public GrantedTokenInfo grantToken(String code, String redirectUri, AuthVendor authVendor) throws UnsupportedCodeException {
        GrantedTokenInfo tokenFrom3rdParty = super.grantToken(code, redirectUri, authVendor);
        String oidcId = tokenFrom3rdParty.id();
        User user = findUserPort.findByAuthVendorAndOidcId(authVendor, oidcId)
                .orElse(User.of(authVendor, oidcId));

        OAuth2UserInfo userInfo = user.isGuest()
                ? retrieveUserInfo(tokenFrom3rdParty.accessToken())
                : null;

        String accessToken = jwtUtil.generateAccessToken(oidcId, List.of(user.getRole().getId()), authVendor);
        String refreshToken = jwtUtil.generateRefreshToken(oidcId, List.of(user.getRole().getId()), authVendor);

        return GrantedTokenInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .authVendor(authVendor)
                .id(oidcId)
                .userInfo(userInfo)
                .build();
    }

    @Override
    public OAuth2AuthenticationInfo authenticate(String accessToken) throws InvalidAccessTokenException {
        try {
            TokenClaims claims = jwtUtil.parseAccessToken(accessToken);

            return OAuth2AuthenticationInfo.builder()
                    .id(claims.getId())
                    .authVendor(claims.getAuthVendor())
                    .build();
        } catch (JwtException e) {
            throw new InvalidAccessTokenException(e.getMessage());
        }
    }

    @Override
    public GrantedTokenInfo refreshToken(String refreshToken) throws InvalidRefreshTokenException {
        try {
            TokenClaims tokenClaims = jwtUtil.parseRefreshToken(refreshToken);

            return GrantedTokenInfo.builder()
                    .accessToken(jwtUtil.generateAccessToken(tokenClaims.getId(), tokenClaims.getRoleIds(), tokenClaims.getAuthVendor()))
                    .refreshToken(jwtUtil.generateRefreshToken(tokenClaims.getId(), tokenClaims.getRoleIds(), tokenClaims.getAuthVendor()))
                    .id(tokenClaims.getId())
                    .authVendor(tokenClaims.getAuthVendor())
                    .build();
        } catch (JwtException e) {
            throw new InvalidRefreshTokenException("Invalid refresh token", e);
        }
    }
}
