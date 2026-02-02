package com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller.apidocs.RegisterFasstoWarehousingApiDocs;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.mapper.FasstoWarehousingRequestToCommandMapper;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.request.RegisterFasstoWarehousingRequest;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.response.RegisterFasstoWarehousingResponse;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoWarehousingResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.RegisterFasstoWarehousingUseCase;
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
@RequestMapping("/api/v1/vendors/fassto/warehousing")
@Tag(name = "파스토 상품 입고 API", description = "파스토 상품 입고 관련 API")
@RequiredArgsConstructor
public class FasstoWarehousingController {
    private final RegisterFasstoWarehousingUseCase registerFasstoWarehousingUseCase;

    /**
     * (관리자) 파스토 상품 입고 요청
     *
     * @param customerCode 파스토 고객사 코드
     * @param accessToken  파스토 액세스 토큰
     * @param request      입고 요청 정보
     * @Author 성효빈
     * @Date 2026-01-31
     * @Description 파스토 상품 입고 요청을 등록합니다.
     */
    @PostMapping("/{customerCode}")
    @PreAuthorize(ADMIN_POINTCUT)
    @RegisterFasstoWarehousingApiDocs
    public ResponseEntity<BaseResponse<RegisterFasstoWarehousingResponse>> registerWarehousing(
            @PathVariable String customerCode,
            @RequestHeader("accessToken") String accessToken,
            @Valid @RequestBody List<RegisterFasstoWarehousingRequest> request
    ) {
        RegisterFasstoWarehousingResult result = registerFasstoWarehousingUseCase.registerWarehousing(
                FasstoWarehousingRequestToCommandMapper.mapToRegisterCommand(customerCode, accessToken, request)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        RegisterFasstoWarehousingResponse.from(result),
                        HttpStatus.CREATED,
                        DEFAULT_SUCCESS_CODE,
                        "파스토 상품 입고 요청 성공"
                ),
                HttpStatus.CREATED
        );
    }
}
