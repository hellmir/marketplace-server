package com.personal.shop.user.service.authentication.utility;

import com.personal.shop.user.port.in.result.LoginResult;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuth2WebUtils {
    private static final String FRONTEND_REDIRECTION_PATH = "/redirection";
    private static final Pattern REDIRECTION_DESTINATION_PATTERN =
            Pattern.compile("^(http|https)://[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*(:\\d{1,5})?$");

    public static String buildAfterLoginRedirectionUrl(String redirectionDestination, LoginResult loginResult, String authVendor) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(redirectionDestination)
                .path(FRONTEND_REDIRECTION_PATH)
                .queryParam("type", "oauth2")
                .queryParam("is-success", true)
                .queryParam("access-token", loginResult.getAccessToken())
                .queryParam("is-guest", loginResult.isNewUser())
                .queryParam("auth-vendor", authVendor);

        if (loginResult.isNewUser()) {
            // 소셜 로그인 개인정보 동의항목 정보 세팅
            uriComponentsBuilder = addParameterIfNotNull(uriComponentsBuilder, "user-name", loginResult.getNickname());
        } else {
            uriComponentsBuilder = uriComponentsBuilder.queryParam("user-id");
        }
        return uriComponentsBuilder.encode().toUriString();
    }

    private static UriComponentsBuilder addParameterIfNotNull(UriComponentsBuilder builder, String name, String value) {
        if (value != null) {
            return builder.queryParam(name, value);
        }
        return builder;
    }

    public static String extractDestination(String clientUrl) {
        if (!StringUtils.hasText(clientUrl)) {
            return null;
        }

        URI uri = URI.create(clientUrl);

        if (uri.getAuthority() == null) {
            return null;
        }

        String redirectionTarget = uri.getScheme() + "://" + uri.getAuthority();

        Matcher matcher = REDIRECTION_DESTINATION_PATTERN.matcher(redirectionTarget);
        return matcher.matches()
                ? redirectionTarget
                : null;
    }
}
