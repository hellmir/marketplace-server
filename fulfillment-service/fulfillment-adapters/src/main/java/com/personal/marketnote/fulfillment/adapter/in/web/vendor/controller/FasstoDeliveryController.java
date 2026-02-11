package com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller.apidocs.GetFasstoDeliveriesApiDocs;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller.apidocs.GetFasstoDeliveryDetailApiDocs;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller.apidocs.RegisterFasstoDeliveryApiDocs;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.mapper.FasstoDeliveryRequestToCommandMapper;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.request.RegisterFasstoDeliveryRequest;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.response.GetFasstoDeliveriesResponse;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.response.GetFasstoDeliveryDetailResponse;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.response.RegisterFasstoDeliveryResponse;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoDeliveriesResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoDeliveryDetailResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoDeliveryResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoDeliveriesUseCase;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoDeliveryDetailUseCase;
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
    private final GetFasstoDeliveriesUseCase getFasstoDeliveriesUseCase;
    private final GetFasstoDeliveryDetailUseCase getFasstoDeliveryDetailUseCase;

    /**
     * (관리자) 파스토 출고 등록 요청
     *
     * @param customerCode 파스토 고객사 코드
     * @param accessToken  파스토 액세스 토큰
     * @param request      출고 등록 요청 정보
     * @Author 성효빈
     * @Date 2026-02-11
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

    /**
     * (관리자) 파스토 출고 목록 조회
     *
     * @param customerCode 파스토 고객사 코드
     * @param accessToken  파스토 액세스 토큰
     * @param startDate    조회 시작일(YYYY-MM-DD)
     * @param endDate      조회 종료일(YYYY-MM-DD)
     * @param status       작업상태 코드(ALL:전체, ORDER:출고요청, WORKING:출고작업중, DONE:출고완료, PARTDONE:부분출고,
     *                     CANCEL:출고요청취소, SHORTAGE:재고부족결품)
     * @param outDiv       출고 구분(1:택배, 2:차량배송)
     * @param ordNo        고객사 주문번호
     * @Author 성효빈
     * @Date 2026-02-11
     * @Description 파스토 출고 목록을 조회합니다.
     */
    @GetMapping("/{customerCode}/{startDate}/{endDate}/{status}/{outDiv}")
    @PreAuthorize(ADMIN_POINTCUT)
    @GetFasstoDeliveriesApiDocs
    public ResponseEntity<BaseResponse<GetFasstoDeliveriesResponse>> getDeliveries(
            @PathVariable String customerCode,
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable String status,
            @PathVariable String outDiv,
            @RequestHeader("accessToken") String accessToken,
            @RequestParam(required = false) String ordNo
    ) {
        GetFasstoDeliveriesResult result = getFasstoDeliveriesUseCase.getDeliveries(
                FasstoDeliveryRequestToCommandMapper.mapToDeliveriesCommand(
                        customerCode,
                        accessToken,
                        startDate,
                        endDate,
                        status,
                        outDiv,
                        ordNo
                )
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        GetFasstoDeliveriesResponse.from(result),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "파스토 출고 목록 조회 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * (관리자) 파스토 출고 상세 조회
     *
     * @param customerCode 파스토 고객사 코드
     * @param slipNo       파스토 출고요청번호
     * @param accessToken  파스토 액세스 토큰
     * @param ordNo        주문번호
     * @Author 성효빈
     * @Date 2026-02-12
     * @Description 파스토 출고 상세 정보를 조회합니다.
     */
    @GetMapping("/detail/{customerCode}/{slipNo}")
    @PreAuthorize(ADMIN_POINTCUT)
    @GetFasstoDeliveryDetailApiDocs
    public ResponseEntity<BaseResponse<GetFasstoDeliveryDetailResponse>> getDeliveryDetail(
            @PathVariable String customerCode,
            @PathVariable String slipNo,
            @RequestHeader("accessToken") String accessToken,
            @RequestParam(required = false) String ordNo
    ) {
        GetFasstoDeliveryDetailResult result = getFasstoDeliveryDetailUseCase.getDeliveryDetail(
                FasstoDeliveryRequestToCommandMapper.mapToDeliveryDetailCommand(customerCode, accessToken, slipNo, ordNo)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        GetFasstoDeliveryDetailResponse.from(result),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "파스토 출고 상세 조회 성공"
                ),
                HttpStatus.OK
        );
    }
}
