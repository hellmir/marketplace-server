package com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller.apidocs.GetFasstoWarehousesApiDocs;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller.apidocs.RegisterFasstoWarehouseApiDocs;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller.apidocs.UpdateFasstoWarehouseApiDocs;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.mapper.FasstoWarehouseRequestToCommandMapper;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.request.RegisterFasstoWarehouseRequest;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.request.UpdateFasstoWarehouseRequest;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.response.GetFasstoWarehousesResponse;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.response.RegisterFasstoWarehouseResponse;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.response.UpdateFasstoWarehouseResponse;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousesResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoWarehouseResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.UpdateFasstoWarehouseResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoWarehousesUseCase;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.RegisterFasstoWarehouseUseCase;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.UpdateFasstoWarehouseUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;
import static com.personal.marketnote.common.utility.ApiConstant.ADMIN_POINTCUT;

@RestController
@RequestMapping("/api/v1/vendors/fassto/warehouses")
@Tag(name = "파스토 출고처 API", description = "파스토 출고처 관련 API")
@RequiredArgsConstructor
public class FasstoWarehouseController {
    private final RegisterFasstoWarehouseUseCase registerFasstoWarehouseUseCase;
    private final GetFasstoWarehousesUseCase getFasstoWarehousesUseCase;
    private final UpdateFasstoWarehouseUseCase updateFasstoWarehouseUseCase;

    /**
     * (관리자) 파스토 출고처 등록 요청
     *
     * @param customerCode 파스토 고객사 코드
     * @param accessToken  파스토 액세스 토큰
     * @param request      출고처 등록 요청 정보
     * @Author 성효빈
     * @Date 2026-01-25
     * @Description 파스토 출고처 등록을 요청합니다.
     */
    @PostMapping("/{customerCode}")
    @PreAuthorize(ADMIN_POINTCUT)
    @RegisterFasstoWarehouseApiDocs
    public ResponseEntity<BaseResponse<RegisterFasstoWarehouseResponse>> registerWarehouse(
            @PathVariable String customerCode,
            @RequestHeader("accessToken") String accessToken,
            @Valid @RequestBody RegisterFasstoWarehouseRequest request
    ) {
        RegisterFasstoWarehouseResult result = registerFasstoWarehouseUseCase.registerWarehouse(
                FasstoWarehouseRequestToCommandMapper.mapToRegisterCommand(customerCode, accessToken, request)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        RegisterFasstoWarehouseResponse.from(result),
                        HttpStatus.CREATED,
                        DEFAULT_SUCCESS_CODE,
                        "파스토 출고처 등록 성공"
                ),
                HttpStatus.CREATED
        );
    }

    /**
     * (관리자) 파스토 출고처 목록 조회
     *
     * @param customerCode 파스토 고객사 코드
     * @param accessToken  파스토 액세스 토큰
     * @Author 성효빈
     * @Date 2026-01-25
     * @Description 파스토 출고처 목록을 조회합니다.
     */
    @GetMapping("/{customerCode}")
    @PreAuthorize(ADMIN_POINTCUT)
    @GetFasstoWarehousesApiDocs
    public ResponseEntity<BaseResponse<GetFasstoWarehousesResponse>> getWarehouses(
            @PathVariable String customerCode,
            @RequestHeader("accessToken") String accessToken
    ) {
        GetFasstoWarehousesResult result = getFasstoWarehousesUseCase.getWarehouses(
                FasstoWarehouseRequestToCommandMapper.mapToWarehousesCommand(customerCode, accessToken)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        GetFasstoWarehousesResponse.from(result),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "파스토 출고처 목록 조회 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * (관리자) 파스토 출고처 수정 요청
     *
     * @param customerCode 파스토 고객사 코드
     * @param accessToken  파스토 액세스 토큰
     * @param request      출고처 수정 요청 정보
     * @Author 성효빈
     * @Date 2026-01-25
     * @Description 파스토 출고처를 수정합니다.
     */
    @PutMapping("/{customerCode}")
    @PreAuthorize(ADMIN_POINTCUT)
    @UpdateFasstoWarehouseApiDocs
    public ResponseEntity<BaseResponse<UpdateFasstoWarehouseResponse>> updateWarehouse(
            @PathVariable String customerCode,
            @RequestHeader("accessToken") String accessToken,
            @Valid @RequestBody UpdateFasstoWarehouseRequest request
    ) {
        UpdateFasstoWarehouseResult result = updateFasstoWarehouseUseCase.updateWarehouse(
                FasstoWarehouseRequestToCommandMapper.mapToUpdateCommand(customerCode, accessToken, request)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        UpdateFasstoWarehouseResponse.from(result),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "파스토 출고처 수정 성공"
                ),
                HttpStatus.OK
        );
    }
}
