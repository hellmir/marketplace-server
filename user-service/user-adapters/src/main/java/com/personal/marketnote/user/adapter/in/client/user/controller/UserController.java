package com.personal.marketnote.user.adapter.in.client.user.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.utility.ElementExtractor;
import com.personal.marketnote.common.utility.FormatConverter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.user.adapter.in.client.user.controller.apidocs.*;
import com.personal.marketnote.user.adapter.in.client.user.mapper.UserRequestToCommandMapper;
import com.personal.marketnote.user.adapter.in.client.user.request.SignInRequest;
import com.personal.marketnote.user.adapter.in.client.user.request.SignOutRequest;
import com.personal.marketnote.user.adapter.in.client.user.request.SignUpRequest;
import com.personal.marketnote.user.adapter.in.client.user.request.UpdateUserInfoRequest;
import com.personal.marketnote.user.adapter.in.client.user.response.*;
import com.personal.marketnote.user.port.in.result.SignInResult;
import com.personal.marketnote.user.port.in.result.SignUpResult;
import com.personal.marketnote.user.port.in.result.WithdrawResult;
import com.personal.marketnote.user.port.in.usecase.user.*;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import com.personal.marketnote.user.utility.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;
import static com.personal.marketnote.common.security.token.utility.TokenConstant.ISS_CLAIM_KEY;
import static com.personal.marketnote.common.security.token.utility.TokenConstant.SUB_CLAIM_KEY;

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
@Tag(name = "회원 API", description = "회원 관련 API")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final SignUpUseCase signUpUseCase;
    private final SignInUseCase signInUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final RegisterReferredUserCodeUseCase registerReferredUserCodeUseCase;
    private final WithdrawUseCase withdrawUseCase;
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
    public ResponseEntity<BaseResponse<SignUpResponse>> signUpUser(
            @Valid @RequestBody SignUpRequest signUpRequest,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal,
            HttpServletRequest request
    ) {
        AuthVendor authVendor = AuthVendor.NATIVE;
        String oidcId = null;

        if (FormatValidator.hasValue(principal)) {
            oidcId = principal.getAttribute(SUB_CLAIM_KEY);
            String issuer = principal.getAttribute(ISS_CLAIM_KEY);
            authVendor = resolveVendorFromIssuer(FormatConverter.toUpperCase(issuer));
        }

        String clientIp = extractClientIp(request);

        SignUpResult signUpResult = signUpUseCase.signUp(
                UserRequestToCommandMapper.mapToCommand(signUpRequest), authVendor, oidcId, clientIp
        );

        List<String> roleIds = List.of(signUpResult.roleId());
        Long id = signUpResult.id();
        String subject = String.valueOf(signUpResult.id());
        String accessToken = jwtUtil.generateAccessToken(subject, id, roleIds, authVendor);
        String refreshToken = jwtUtil.generateRefreshToken(subject, id, roleIds, authVendor);

        HttpStatus httpStatus = HttpStatus.CREATED;
        boolean isNewUser = signUpResult.isNewUser();
        if (isNewUser) {
            httpStatus = HttpStatus.OK;
        }

        return new ResponseEntity<>(
                BaseResponse.of(
                        SignUpResponse.of(accessToken, refreshToken, isNewUser),
                        httpStatus,
                        DEFAULT_SUCCESS_CODE,
                        "회원 가입 성공"
                ),
                httpStatus
        );
    }

    /**
     * 추천 회원 초대 코드 등록
     *
     * @param referredUserCode 추천 회원의 초대 코드
     * @param principal        사용자 인증 정보
     * @Author 성효빈
     * @Date 2025-12-28
     * @Description 자신을 추천한 회원의 초대 코드를 등록합니다.
     */
    @PatchMapping("/referred-user-code")
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
                        DEFAULT_SUCCESS_CODE,
                        "초대 코드 등록 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * 회원 로그인
     *
     * @param signInRequest 로그인 요청 요청
     * @param principal     사용자 인증 정보
     * @return 인증 토큰 응답 {@link AuthenticationTokenResponse}
     * @Author 성효빈
     * @Date 2025-12-28
     * @Description 회원 로그인을 수행합니다.
     */
    @PostMapping("/sign-in")
    @SignInApiDocs
    public ResponseEntity<BaseResponse<SignInResponse>> signInUser(
            @Valid @RequestBody SignInRequest signInRequest,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal,
            HttpServletRequest request
    ) {
        AuthVendor authVendor = AuthVendor.NATIVE;
        String oidcId = null;

        if (FormatValidator.hasValue(principal)) {
            oidcId = principal.getAttribute(SUB_CLAIM_KEY);
            String issuer = principal.getAttribute(ISS_CLAIM_KEY);
            authVendor = resolveVendorFromIssuer(FormatConverter.toUpperCase(issuer));
        }

        String clientIp = extractClientIp(request);

        SignInResult signInResult = signInUseCase.signIn(
                UserRequestToCommandMapper.mapToCommand(signInRequest), authVendor, oidcId, clientIp
        );

        List<String> roleIds = List.of(signInResult.roleId());
        Long id = signInResult.id();
        String subject = String.valueOf(signInResult.id());
        String accessToken = jwtUtil.generateAccessToken(subject, id, roleIds, authVendor);
        String refreshToken = jwtUtil.generateRefreshToken(subject, id, roleIds, authVendor);

        return new ResponseEntity<>(
                BaseResponse.of(
                        SignInResponse.of(
                                accessToken, refreshToken,
                                signInResult.isRequiredTermsAgreed()
                        ),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "회원 로그인 성공"
                ),
                HttpStatus.OK
        );
    }

    private String extractClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (FormatValidator.hasValue(xff)) {
            return xff.split(",")[0].trim();
        }

        String realIp = request.getHeader("X-Real-IP");
        if (FormatValidator.hasValue(realIp)) {
            return realIp;
        }

        return request.getRemoteAddr();
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
     * 자신의 정보 조회
     *
     * @param principal 사용자 인증 정보
     * @return 회원 정보 조회 응답 {@link GetUserInfoResponse}
     * @Author 성효빈
     * @Date 2025-12-28
     * @Description 자신의 정보를 조회합니다.
     */
    @GetMapping("/me")
    @GetMyInfoApiDocs
    public ResponseEntity<BaseResponse<GetUserInfoResponse>> getMyInfo(
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        GetUserInfoResponse getUserInfoResponse = GetUserInfoResponse.from(
                getUserUseCase.getUserInfo(ElementExtractor.extractUserId(principal))
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        getUserInfoResponse,
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "자신의 정보 조회 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * 자신의 정보 수정
     *
     * @param updateUserInfoRequest 자신의 정보 수정 요청
     * @param principal             사용자 인증 정보
     * @return 회원 정보 수정 응답 {@link Void}
     * @Author 성효빈
     * @Date 2025-12-27
     * @Description 회원 정보를 수정합니다.
     */
    @PatchMapping
    @UpdateMyInfoApiDocs
    public ResponseEntity<BaseResponse<Void>> updateMyInfo(
            @Valid @RequestBody UpdateUserInfoRequest updateUserInfoRequest,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        updateUserUseCase.updateUserInfo(
                false,
                ElementExtractor.extractUserId(principal),
                UserRequestToCommandMapper.mapToCommand(updateUserInfoRequest)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        null,
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "자신의 정보 수정 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * 회원 로그아웃
     *
     * @param signOutRequest 로그아웃 요청
     * @return 로그아웃 응답 {@link SignOutResponse}
     * @Author 성효빈
     * @Date 2025-12-28
     * @Description 로그아웃합니다.
     */
    @DeleteMapping("/sign-out")
    @SignOutApiDocs
    public ResponseEntity<BaseResponse<SignOutResponse>> signOutUser(@Valid @RequestBody SignOutRequest signOutRequest) {
        String accessToken = jwtUtil.revokeToken(signOutRequest.getAccessToken());
        String refreshToken = jwtUtil.revokeToken(signOutRequest.getRefreshToken());

        return new ResponseEntity<>(
                BaseResponse.of(
                        SignOutResponse.of(accessToken, refreshToken),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "회원 로그아웃 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * 회원 탈퇴
     *
     * @param principal         사용자 인증 정보
     * @param googleAccessToken Google 액세스 토큰
     * @return 회원 탈퇴 응답 {@link WithdrawResponse}
     * @Author 성효빈
     * @Date 2025-12-29
     * @Description 회원 탈퇴를 수행합니다.
     */
    @DeleteMapping
    @WithdrawalApiDocs
    public ResponseEntity<BaseResponse<WithdrawResponse>> withdrawUser(
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal,
            @RequestHeader(value = "X-Google-Access-Token", required = false) String googleAccessToken
    ) {
        WithdrawResult result = withdrawUseCase.withdrawUser(
                ElementExtractor.extractUserId(principal), googleAccessToken
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        WithdrawResponse.from(result),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "회원 탈퇴 성공"
                ),
                HttpStatus.OK
        );
    }
}
