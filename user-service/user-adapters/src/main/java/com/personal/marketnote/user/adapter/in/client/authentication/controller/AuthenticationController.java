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


/**
 * 인증 컨트롤러
 *
 * @Author 성효빈
 * @Date 2025-12-28
 * @Description 인증 관련 API를 제공합니다.
 */
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

    /**
     * OAuth2 로그인
     *
     * @param authVendor OAuth2 인증 제공자
     * @param code       OAuth2 인증 코드
     * @param state      OAuth2 인증 상태
     * @param request    HTTP 요청
     * @return 인증 토큰 응답 {@link GrantedTokenInfo}
     * @Author 성효빈
     * @Date 2025-12-28
     * @Description OAuth2 로그인을 수행합니다.
     */
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

    /**
     * Access Token 재발급
     *
     * @param refreshAccessTokenRequest 리프레시 토큰 요청
     * @return 재발급된 Access Token 응답 {@link RefreshedAccessTokenResponse}
     * @Author 성효빈
     * @Date 2025-12-28
     * @Description Access Token을 재발급합니다.
     */
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
