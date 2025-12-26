package com.personal.shop.user.adapter.in.client.authentication.controller;

import com.personal.shop.common.adapter.in.api.format.BaseResponse;
import com.personal.shop.user.adapter.in.client.authentication.controller.apidocs.RefreshAccessTokenApiDocs;
import com.personal.shop.user.adapter.in.client.authentication.response.RefreshedAccessTokenResponse;
import com.personal.shop.user.adapter.in.client.authentication.response.WebBasedTokenRefreshResponse;
import com.personal.shop.user.adapter.out.vendor.authentication.WebBasedAuthenticationServiceAdapter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
@Tag(
        name = "인증 API",
        description = "인증 관련 API"
)
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {
    private final WebBasedAuthenticationServiceAdapter authServiceAdapter;

    @RefreshAccessTokenApiDocs
    @PostMapping("/access-token/refresh")
    public ResponseEntity<BaseResponse<RefreshedAccessTokenResponse>> accessToken(
            @CookieValue(value = "refresh_token", required = false) String refreshToken
    ) {
        WebBasedTokenRefreshResponse response = authServiceAdapter.issueNewAccessToken(refreshToken);

        return new ResponseEntity<>(
                BaseResponse.of(response.accessToken(), HttpStatus.CREATED),
                response.headers(),
                HttpStatus.CREATED
        );
    }
}
