package com.personal.marketnote.user.adapter.out.vendor.authentication;

import com.personal.marketnote.common.utility.http.cookie.HttpCookieName;
import com.personal.marketnote.common.utility.http.cookie.HttpCookieObject;
import com.personal.marketnote.common.utility.http.cookie.HttpCookieUtils;
import com.personal.marketnote.user.adapter.in.client.authentication.request.OAuth2LoginRequest;
import com.personal.marketnote.user.adapter.in.client.authentication.response.OAuth2LoginResponse;
import com.personal.marketnote.user.adapter.in.client.authentication.response.RefreshedAccessTokenResponse;
import com.personal.marketnote.user.adapter.in.client.authentication.response.WebBasedTokenRefreshResponse;
import com.personal.marketnote.user.port.in.result.LoginResult;
import com.personal.marketnote.user.port.in.usecase.LoginUseCase;
import com.personal.marketnote.user.security.token.dto.GrantedTokenInfo;
import com.personal.marketnote.user.security.token.exception.InvalidRefreshTokenException;
import com.personal.marketnote.user.security.token.support.TokenSupport;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import com.personal.marketnote.user.service.authentication.utility.OAuth2WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

@Component
@Slf4j
public class WebBasedAuthenticationServiceAdapter {
    private static final long REFRESH_TOKEN_COOKIE_MAX_AGE = 2592000000L;
    private static final Pattern REDIRECTION_DESTINATION_PATTERN
            = Pattern.compile("^(http|https)://[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*(:\\d{1,5})?$");
    private final LoginUseCase loginUseCase;
    private final TokenSupport tokenSupport;
    private final String serverOrigin;
    private final String serverPort;
    private final String clientOrigin;
    private final HttpCookieUtils httpCookieUtils;

    public WebBasedAuthenticationServiceAdapter(
            LoginUseCase loginUseCase,
            TokenSupport tokenSupport,
            @Value("${server.origin}") String serverOrigin,
            @Value("${server.port}") String serverPort,
            @Value("${client.origin}") List<String> clientOrigins,
            HttpCookieUtils httpCookieUtils
    ) {
        this.loginUseCase = loginUseCase;
        this.tokenSupport = tokenSupport;
        this.serverOrigin = serverOrigin;
        this.serverPort = serverPort;
        this.clientOrigin = clientOrigins.getFirst();
        this.httpCookieUtils = httpCookieUtils;
    }

    public OAuth2LoginResponse loginByOAuth2(OAuth2LoginRequest oAuth2LoginRequest) {
        HttpServletRequest servletRequest = oAuth2LoginRequest.request();
        String redirectUri = serverOrigin + ":" + serverPort + servletRequest.getRequestURI();

        LoginResult loginResult =
                loginUseCase.loginByOAuth2(oAuth2LoginRequest.code(), redirectUri, AuthVendor.valueOfIgnoreCase(oAuth2LoginRequest.authVendor()));

        String redirectionDestination = getRedirectionDestination(oAuth2LoginRequest.state());
        log.debug("redirectionDestination={}", redirectionDestination);

        HttpCookieObject refreshTokenCookie = httpCookieUtils.generateHttpOnlyCookie(
                HttpCookieName.REFRESH_TOKEN,
                loginResult.getRefreshToken(),
                REFRESH_TOKEN_COOKIE_MAX_AGE
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.asSetCookieHeaderValue());

        headers.add(
                HttpHeaders.LOCATION,
                OAuth2WebUtils.buildAfterLoginRedirectionUrl(redirectionDestination, loginResult, oAuth2LoginRequest.authVendor())
        );

        log.info("Redirect URI: {}", redirectUri);

        if (log.isDebugEnabled()) {
            log.debug("code: {}", oAuth2LoginRequest.code());
            log.debug("Auth vendor: {}", oAuth2LoginRequest.authVendor());
            log.debug("state: {}", oAuth2LoginRequest.state());
            log.debug("loginResult={}", loginResult);
            log.debug("refreshTokenCookie={}", refreshTokenCookie.asSetCookieHeaderValue());
        }

        return new OAuth2LoginResponse(headers);
    }

    private String getRedirectionDestination(String client) {
        String destination = OAuth2WebUtils.extractDestination(client);
        if (destination != null) {
            log.debug("From query parameter");
            return destination;
        }

        log.debug("Redirect to default origin");
        return this.clientOrigin;
    }

    public WebBasedTokenRefreshResponse issueNewAccessToken(String refreshToken) {
        if (!StringUtils.hasText(refreshToken)) {
            throw new InvalidRefreshTokenException("refresh_token is null");
        }

        GrantedTokenInfo grantedTokenInfo = tokenSupport.refreshToken(refreshToken);
        log.debug("grantedTokenInfo: {}", grantedTokenInfo);

        HttpHeaders headers = new HttpHeaders();
        if (grantedTokenInfo.refreshToken() != null) {
            HttpCookieObject refreshTokenCookie = this.httpCookieUtils.generateHttpOnlyCookie(
                    HttpCookieName.REFRESH_TOKEN,
                    grantedTokenInfo.refreshToken(),
                    REFRESH_TOKEN_COOKIE_MAX_AGE
            );

            log.debug("refreshTokenCookie: {}", refreshTokenCookie.asSetCookieHeaderValue());

            headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.asSetCookieHeaderValue());
        }

        return new WebBasedTokenRefreshResponse(headers,
                new RefreshedAccessTokenResponse(grantedTokenInfo.accessToken()));
    }

    public HttpHeaders logout() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(
                HttpHeaders.SET_COOKIE,
                this.httpCookieUtils.invalidateCookie(HttpCookieName.USER_ID, false).asSetCookieHeaderValue()
        );
        headers.add(
                HttpHeaders.SET_COOKIE,
                this.httpCookieUtils.invalidateCookie(HttpCookieName.ACCESS_TOKEN, false).asSetCookieHeaderValue()
        );
        headers.add(
                HttpHeaders.SET_COOKIE,
                this.httpCookieUtils.invalidateCookie(HttpCookieName.REFRESH_TOKEN, false).asSetCookieHeaderValue()
        );
        return headers;
    }
}
