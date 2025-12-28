package com.personal.marketnote.user.adapter.in.client.authentication.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.domain.exception.token.UnsupportedCodeException;
import com.personal.marketnote.user.adapter.in.client.authentication.controller.apidocs.Oauth2LoginApiDocs;
import com.personal.marketnote.user.adapter.in.client.authentication.controller.apidocs.RefreshAccessTokenApiDocs;
import com.personal.marketnote.user.adapter.in.client.authentication.request.OAuth2LoginRequest;
import com.personal.marketnote.user.adapter.in.client.authentication.request.RefreshAccessTokenRequest;
import com.personal.marketnote.user.adapter.in.client.authentication.response.OAuth2LoginResponse;
import com.personal.marketnote.user.adapter.in.client.authentication.response.RefreshedAccessTokenResponse;
import com.personal.marketnote.user.adapter.in.client.authentication.response.WebBasedTokenRefreshResponse;
import com.personal.marketnote.user.adapter.out.vendor.authentication.WebBasedAuthenticationServiceAdapter;
import com.personal.marketnote.user.security.token.dto.GrantedTokenInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Oauth2LoginApiDocs
    @GetMapping("/{authVendor}/redirect-uri")
    public ResponseEntity<BaseResponse<GrantedTokenInfo>> oauth2Login(
            @PathVariable("authVendor") String authVendor,
            @RequestParam("code") String code,
            @RequestParam(value = "state", required = false) String state,
            HttpServletRequest request
    ) throws UnsupportedCodeException {
        OAuth2LoginResponse response = authServiceAdapter.loginByOAuth2(
                new OAuth2LoginRequest(authVendor, code, state, request)
        );

        return new ResponseEntity<>(response.headers(), HttpStatus.PERMANENT_REDIRECT);
    }

    @RefreshAccessTokenApiDocs
    @PostMapping("/access-token/refresh")
    public ResponseEntity<BaseResponse<RefreshedAccessTokenResponse>> accessToken(
            @RequestBody RefreshAccessTokenRequest refreshAccessTokenRequest
    ) {
        WebBasedTokenRefreshResponse response
                = authServiceAdapter.issueNewAccessToken(refreshAccessTokenRequest.getRefreshToken());

        return new ResponseEntity<>(
                BaseResponse.of(response.accessToken(), HttpStatus.CREATED, "Access Token 재발급 성공"),
                response.headers(),
                HttpStatus.CREATED
        );
    }
}
