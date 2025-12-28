package com.personal.marketnote.user.adapter.in.client.user.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.utility.ElementExtractor;
import com.personal.marketnote.common.utility.FormatConverter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.user.adapter.in.client.authentication.response.GetUserResponse;
import com.personal.marketnote.user.adapter.in.client.user.controller.apidocs.GetUserInfoApiDocs;
import com.personal.marketnote.user.adapter.in.client.user.controller.apidocs.SignInApiDocs;
import com.personal.marketnote.user.adapter.in.client.user.controller.apidocs.SignUpApiDocs;
import com.personal.marketnote.user.adapter.in.client.user.controller.apidocs.UpdateUserInfoApiDocs;
import com.personal.marketnote.user.adapter.in.client.user.mapper.UserRequestToCommandMapper;
import com.personal.marketnote.user.adapter.in.client.user.request.SignInRequest;
import com.personal.marketnote.user.adapter.in.client.user.request.SignUpRequest;
import com.personal.marketnote.user.adapter.in.client.user.request.UpdateUserInfoRequest;
import com.personal.marketnote.user.adapter.in.client.user.response.AuthenticationTokenResponse;
import com.personal.marketnote.user.adapter.in.client.user.response.SignInResponse;
import com.personal.marketnote.user.adapter.in.client.user.response.SignUpResponse;
import com.personal.marketnote.user.port.in.usecase.user.GetUserUseCase;
import com.personal.marketnote.user.port.in.usecase.user.SignInUseCase;
import com.personal.marketnote.user.port.in.usecase.user.SignUpUseCase;
import com.personal.marketnote.user.port.in.usecase.user.UpdateUserUseCase;
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
    private final JwtUtil jwtUtil;

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
}
