package com.personal.marketnote.user.adapter.in.client.user.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.domain.exception.illegalargument.novalue.OauthTokenNoValueException;
import com.personal.marketnote.common.utility.FormatConverter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.user.adapter.in.client.user.controller.apidocs.SignUpApiDocs;
import com.personal.marketnote.user.adapter.in.client.user.mapper.UserRequestToCommandMapper;
import com.personal.marketnote.user.adapter.in.client.user.request.SignUpRequest;
import com.personal.marketnote.user.adapter.in.client.user.response.AuthenticationTokenResponse;
import com.personal.marketnote.user.adapter.in.client.user.response.SignUpResponse;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import com.personal.marketnote.user.service.user.SignUpService;
import com.personal.marketnote.user.utility.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(
        name = "회원 API",
        description = "회원 관련 API"
)
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final SignUpService signUpService;
    private final JwtUtil jwtUtil;

    @PostMapping
    @SignUpApiDocs
    public ResponseEntity<BaseResponse<AuthenticationTokenResponse>> signUpUser(
            @Valid @RequestBody SignUpRequest signUpRequest,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        if (!FormatValidator.hasValue(principal)) {
            throw new OauthTokenNoValueException();
        }

        String oidcId = principal.getAttribute("sub");
        String issuer = principal.getAttribute("iss");
        AuthVendor authVendor = resolveVendorFromIssuer(FormatConverter.toUpperCase(issuer));

        SignUpResponse signUpResponse = SignUpResponse.from(
                signUpService.signUp(UserRequestToCommandMapper.mapToCommand(signUpRequest), authVendor, oidcId)
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
