package com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller.apidocs.RegisterFasstoDeliveryApiDocs;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.mapper.FasstoDeliveryRequestToCommandMapper;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.request.RegisterFasstoDeliveryRequest;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.response.RegisterFasstoDeliveryResponse;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoDeliveryResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.RegisterFasstoDeliveryUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;
import static com.personal.marketnote.common.utility.ApiConstant.ADMIN_POINTCUT;

@RestController
@RequestMapping("/api/v1/vendors/fassto/deliveries")
@Tag(name = "파스토 출고 API", description = "파스토 출고 관련 API")
@RequiredArgsConstructor
public class FasstoDeliveryController {
    private final RegisterFasstoDeliveryUseCase registerFasstoDeliveryUseCase;

    /**
     * (관리자) 파스토 출고 등록 요청
     *
     * @param customerCode 파스토 고객사 코드
     * @param accessToken  파스토 액세스 토큰
     * @param request      출고 등록 요청 정보
     * @Author 성효빈
     * @Date 2026-02-10
     * @Description 파스토 출고(택배) 등록을 요청합니다.
     */
    @PostMapping("/{customerCode}")
    @PreAuthorize(ADMIN_POINTCUT)
    @RegisterFasstoDeliveryApiDocs
    public ResponseEntity<BaseResponse<RegisterFasstoDeliveryResponse>> registerDelivery(
            @PathVariable String customerCode,
            @RequestHeader("accessToken") String accessToken,
            @Valid @RequestBody List<RegisterFasstoDeliveryRequest> request
    ) {
        RegisterFasstoDeliveryResult result = registerFasstoDeliveryUseCase.registerDelivery(
                FasstoDeliveryRequestToCommandMapper.mapToRegisterCommand(customerCode, accessToken, request)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        RegisterFasstoDeliveryResponse.from(result),
                        HttpStatus.CREATED,
                        DEFAULT_SUCCESS_CODE,
                        "파스토 출고 등록 성공"
                ),
                HttpStatus.CREATED
        );
    }
}
