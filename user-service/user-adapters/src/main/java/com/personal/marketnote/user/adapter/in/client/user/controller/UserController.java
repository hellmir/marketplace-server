package com.personal.marketnote.user.adapter.in.client.user.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.utility.ElementExtractor;
import com.personal.marketnote.common.utility.FormatConverter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.user.adapter.in.client.authentication.response.GetUserResponse;
import com.personal.marketnote.user.adapter.in.client.user.controller.apidocs.*;
import com.personal.marketnote.user.adapter.in.client.user.mapper.UserRequestToCommandMapper;
import com.personal.marketnote.user.adapter.in.client.user.request.LogoutRequest;
import com.personal.marketnote.user.adapter.in.client.user.request.SignInRequest;
import com.personal.marketnote.user.adapter.in.client.user.request.SignUpRequest;
import com.personal.marketnote.user.adapter.in.client.user.request.UpdateUserInfoRequest;
import com.personal.marketnote.user.adapter.in.client.user.response.AuthenticationTokenResponse;
import com.personal.marketnote.user.adapter.in.client.user.response.LogoutResponse;
import com.personal.marketnote.user.adapter.in.client.user.response.SignInResponse;
import com.personal.marketnote.user.adapter.in.client.user.response.SignUpResponse;
import com.personal.marketnote.user.port.in.usecase.user.*;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import com.personal.marketnote.user.utility.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.personal.marketnote.user.security.token.utility.TokenConstant.ISS_CLAIM_KEY;
import static com.personal.marketnote.user.security.token.utility.TokenConstant.SUB_CLAIM_KEY;

// TODO: 로그인 내역 기능 추가

/**
 * 회원 컨트롤러
 *
 * @Author 성효빈
 * @Date 2025-12-28
 * @Description 회원 관련 API를 제공합니다.
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(
        name = "회원 API",
        description = "회원 관련 API"
)
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final SignUpUseCase signUpUseCase;
    private final SignInUseCase signInUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final RegisterReferredUserCodeUseCase registerReferredUserCodeUseCase;
    private final JwtUtil jwtUtil;

    /**
     * 회원 가입
     *
     * @param signUpRequest 회원 가입 요청
     * @param principal     OAuth2 인증 정보
     * @return 인증 토큰 응답 {@link AuthenticationTokenResponse}
     * @Author 성효빈
     * @Date 2025-12-28
     * @Description 회원으로 가입합니다.
     */
    @PostMapping("/sign-up")
    @SignUpApiDocs
    public ResponseEntity<BaseResponse<AuthenticationTokenResponse>> signUpUser(
            @Valid @RequestBody SignUpRequest signUpRequest,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        AuthVendor authVendor = AuthVendor.NATIVE;
        String oidcId = null;

        if (FormatValidator.hasValue(principal)) {
            oidcId = principal.getAttribute(SUB_CLAIM_KEY);
            String issuer = principal.getAttribute(ISS_CLAIM_KEY);
            authVendor = resolveVendorFromIssuer(FormatConverter.toUpperCase(issuer));
        }

        SignUpResponse signUpResponse = SignUpResponse.from(
                signUpUseCase.signUp(UserRequestToCommandMapper.mapToCommand(signUpRequest), authVendor, oidcId)
        );

        List<String> roleIds = List.of(signUpResponse.roleId());
        Long id = signUpResponse.id();
        String subject = String.valueOf(signUpResponse.id());
        String accessToken = jwtUtil.generateAccessToken(subject, id, roleIds, authVendor);
        String refreshToken = jwtUtil.generateRefreshToken(subject, id, roleIds, authVendor);

        return new ResponseEntity<>(
                BaseResponse.of(
                        new AuthenticationTokenResponse(accessToken, refreshToken),
                        HttpStatus.CREATED,
                        "회원 가입 성공"
                ),
                HttpStatus.CREATED
        );
    }

    /**
     * 추천 회원 초대 코드 등록
     *
     * @param referredUserCode 추천 회원의 초대 코드
     * @param principal        OAuth2 인증 정보
     * @Author 성효빈
     * @Date 2025-12-28
     * @Description 자신을 추천한 회원의 초대 코드를 등록합니다.
     */
    @PostMapping("/referred-user-code")
    @RegisterReferredUserCodeApiDocs
    public ResponseEntity<BaseResponse<Void>> registerReferredUserCode(
            @Valid @RequestParam String referredUserCode,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        registerReferredUserCodeUseCase.registerReferredUserCode(
                ElementExtractor.extractUserId(principal), referredUserCode
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        null,
                        HttpStatus.OK,
                        "초대 코드 등록 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * 회원 로그인
     *
     * @param signInRequest 로그인 요청 요청
     * @param principal     OAuth2 인증 정보
     * @return 인증 토큰 응답 {@link AuthenticationTokenResponse}
     * @Author 성효빈
     * @Date 2025-12-28
     * @Description 회원 로그인을 수행합니다.
     */
    @PostMapping("/sign-in")
    @SignInApiDocs
    public ResponseEntity<BaseResponse<AuthenticationTokenResponse>> signInUser(
            @Valid @RequestBody SignInRequest signInRequest,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        AuthVendor authVendor = AuthVendor.NATIVE;
        String oidcId = null;

        if (FormatValidator.hasValue(principal)) {
            oidcId = principal.getAttribute(SUB_CLAIM_KEY);
            String issuer = principal.getAttribute(ISS_CLAIM_KEY);
            authVendor = resolveVendorFromIssuer(FormatConverter.toUpperCase(issuer));
        }

        SignInResponse signInResponse = SignInResponse.from(
                signInUseCase.signIn(UserRequestToCommandMapper.mapToCommand(signInRequest), authVendor, oidcId)
        );

        List<String> roleIds = List.of(signInResponse.roleId());
        Long id = signInResponse.id();
        String subject = String.valueOf(signInResponse.id());
        String accessToken = jwtUtil.generateAccessToken(subject, id, roleIds, authVendor);
        String refreshToken = jwtUtil.generateRefreshToken(subject, id, roleIds, authVendor);

        return new ResponseEntity<>(
                BaseResponse.of(
                        new AuthenticationTokenResponse(accessToken, refreshToken),
                        HttpStatus.OK,
                        "회원 로그인 성공"
                ),
                HttpStatus.OK
        );
    }

    private AuthVendor resolveVendorFromIssuer(String issuer) {
        if (issuer.contains(AuthVendor.KAKAO.name())) {
            return AuthVendor.KAKAO;
        }

        if (issuer.contains(AuthVendor.GOOGLE.name())) {
            return AuthVendor.GOOGLE;
        }

        if (issuer.contains(AuthVendor.APPLE.name())) {
            return AuthVendor.APPLE;
        }

        return AuthVendor.NATIVE;
    }

    /**
     * 회원 정보 조회
     *
     * @param principal OAuth2 인증 정보
     * @return 회원 정보 조회 응답 {@link GetUserResponse}
     * @Author 성효빈
     * @Date 2025-12-28
     * @Description 회원 정보를 조회합니다.
     */
    @GetMapping
    @GetUserInfoApiDocs
    public ResponseEntity<BaseResponse<GetUserResponse>> getUserInfo(
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        GetUserResponse getUserResponse = GetUserResponse.from(
                getUserUseCase.getUserInfo(ElementExtractor.extractUserId(principal))
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        getUserResponse,
                        HttpStatus.OK,
                        "회원 정보 조회 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * 회원 정보 수정
     *
     * @param updateUserInfoRequest 회원 정보 수정 요청
     * @param principal             OAuth2 인증 정보
     * @return 회원 정보 수정 응답 {@link Void}
     * @Author 성효빈
     * @Date 2025-12-27
     * @Description 회원 정보를 수정합니다.
     */
    @PatchMapping
    @UpdateUserInfoApiDocs
    public ResponseEntity<BaseResponse<Void>> updateUserInfo(
            @Valid @RequestBody UpdateUserInfoRequest updateUserInfoRequest,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        updateUserUseCase.updateUserInfo(
                ElementExtractor.extractUserId(principal),
                UserRequestToCommandMapper.mapToCommand(updateUserInfoRequest)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        null,
                        HttpStatus.OK,
                        "회원 정보 수정 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * 회원 로그아웃
     *
     * @param logoutRequest 로그아웃 요청
     * @param principal     OAuth2 인증 정보
     * @return 로그아웃 응답 {@link LogoutResponse}
     * @Author 성효빈
     * @Date 2025-12-28
     * @Description 로그아웃합니다.
     */
    @DeleteMapping
    @LogoutApiDocs
    public ResponseEntity<BaseResponse<LogoutResponse>> logout(
            @Valid @RequestBody LogoutRequest logoutRequest,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        String accessToken = jwtUtil.revokeToken(logoutRequest.getAccessToken());
        String refreshToken = jwtUtil.revokeToken(logoutRequest.getRefreshToken());

        return new ResponseEntity<>(
                BaseResponse.of(
                        LogoutResponse.of(accessToken, refreshToken),
                        HttpStatus.OK,
                        "회원 로그아웃 성공"
                ),
                HttpStatus.OK
        );
    }
}
