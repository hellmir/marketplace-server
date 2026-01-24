package com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller.apidocs.RequestFasstoAuthApiDocs;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.response.FasstoAuthTokenResponse;
import com.personal.marketnote.fulfillment.port.in.result.vendor.FasstoAccessTokenResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.RequestFasstoAuthUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;
import static com.personal.marketnote.common.utility.ApiConstant.ADMIN_POINTCUT;

@RestController
@RequestMapping("/api/v1/vendors/fassto/auth")
@Tag(name = "파스토 인증 API", description = "파스토 인증 관련 API")
@RequiredArgsConstructor
public class FasstoAuthController {
    private final RequestFasstoAuthUseCase requestFasstoAuthUseCase;

    /**
     * (관리자) 파스토 엑세스 토큰 요청
     *
     * @Author 성효빈
     * @Date 2026-01-23
     * @Description 파스토 엑세스 토큰 발급을 요청합니다.
     */
    @PostMapping
    @PreAuthorize(ADMIN_POINTCUT)
    @RequestFasstoAuthApiDocs
    public ResponseEntity<BaseResponse<FasstoAuthTokenResponse>> requestAccessToken() {
        FasstoAccessTokenResult result = FasstoAccessTokenResult.from(requestFasstoAuthUseCase.requestAccessToken());

        return new ResponseEntity<>(
                BaseResponse.of(
                        FasstoAuthTokenResponse.from(result),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "파스토 엑세스 토큰 발급 성공"
                ),
                HttpStatus.OK
        );
    }
}
