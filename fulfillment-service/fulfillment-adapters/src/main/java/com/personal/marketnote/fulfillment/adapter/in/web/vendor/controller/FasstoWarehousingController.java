package com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller.apidocs.GetFasstoWarehousingApiDocs;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller.apidocs.GetFasstoWarehousingDetailApiDocs;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller.apidocs.RegisterFasstoWarehousingApiDocs;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller.apidocs.UpdateFasstoWarehousingApiDocs;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.mapper.FasstoWarehousingRequestToCommandMapper;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.request.RegisterFasstoWarehousingRequest;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.request.UpdateFasstoWarehousingRequest;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.response.GetFasstoWarehousingDetailResponse;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.response.GetFasstoWarehousingResponse;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.response.RegisterFasstoWarehousingResponse;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.response.UpdateFasstoWarehousingResponse;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousingDetailResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousingResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoWarehousingResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.UpdateFasstoWarehousingResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoWarehousingDetailUseCase;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoWarehousingUseCase;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.RegisterFasstoWarehousingUseCase;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.UpdateFasstoWarehousingUseCase;
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
    private final GetFasstoWarehousingUseCase getFasstoWarehousingUseCase;
    private final GetFasstoWarehousingDetailUseCase getFasstoWarehousingDetailUseCase;
    private final UpdateFasstoWarehousingUseCase updateFasstoWarehousingUseCase;

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

    /**
     * (관리자) 파스토 상품 입고 목록 조회
     *
     * @param customerCode 파스토 고객사 코드
     * @param accessToken  파스토 액세스 토큰
     * @param startDate    조회 시작일(YYYYMMDD)
     * @param endDate      조회 종료일(YYYYMMDD)
     * @param inWay        입고방법(비어있으면:전체,01:택배,02:차량)
     * @param ordNo        주문번호
     * @param wrkStat      작업상태(비어있으면:전체,1:입고요청,2:센터도착,3:입고검수,4:입고확정,5:입고완료)
     * @Author 성효빈
     * @Date 2026-02-03
     * @Description 파스토 상품 입고 목록을 조회합니다.
     */
    @GetMapping("/{customerCode}/{startDate}/{endDate}")
    @PreAuthorize(ADMIN_POINTCUT)
    @GetFasstoWarehousingApiDocs
    public ResponseEntity<BaseResponse<GetFasstoWarehousingResponse>> getWarehousing(
            @PathVariable String customerCode,
            @PathVariable String startDate,
            @PathVariable String endDate,
            @RequestHeader("accessToken") String accessToken,
            @RequestParam(required = false) String inWay,
            @RequestParam(required = false) String ordNo,
            @RequestParam(required = false) String wrkStat
    ) {
        GetFasstoWarehousingResult result = getFasstoWarehousingUseCase.getWarehousing(
                FasstoWarehousingRequestToCommandMapper.mapToWarehousingQuery(
                        customerCode,
                        accessToken,
                        startDate,
                        endDate,
                        inWay,
                        ordNo,
                        wrkStat
                )
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        GetFasstoWarehousingResponse.from(result),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "파스토 상품 입고 목록 조회 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * (관리자) 파스토 상품 입고 상세 조회
     *
     * @param customerCode 파스토 고객사 코드
     * @param slipNo       파스토 입고요청번호
     * @param accessToken  파스토 액세스 토큰
     * @param ordNo        주문번호
     * @Author 성효빈
     * @Date 2026-02-14
     * @Description 파스토 상품 입고 상세 정보를 조회합니다.
     */
    @GetMapping("/detail/{customerCode}/{slipNo}")
    @PreAuthorize(ADMIN_POINTCUT)
    @GetFasstoWarehousingDetailApiDocs
    public ResponseEntity<BaseResponse<GetFasstoWarehousingDetailResponse>> getWarehousingDetail(
            @PathVariable String customerCode,
            @PathVariable String slipNo,
            @RequestHeader("accessToken") String accessToken,
            @RequestParam(required = false) String ordNo
    ) {
        GetFasstoWarehousingDetailResult result = getFasstoWarehousingDetailUseCase.getWarehousingDetail(
                FasstoWarehousingRequestToCommandMapper.mapToWarehousingDetailCommand(
                        customerCode,
                        accessToken,
                        slipNo,
                        ordNo
                )
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        GetFasstoWarehousingDetailResponse.from(result),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "파스토 상품 입고 상세 조회 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * (관리자) 파스토 상품 입고 수정 요청
     *
     * @param customerCode 파스토 고객사 코드
     * @param accessToken  파스토 액세스 토큰
     * @param request      입고 수정 요청 정보
     * @Author 성효빈
     * @Date 2026-02-05
     * @Description 파스토 상품 입고 요청을 수정합니다.
     */
    @PutMapping("/{customerCode}")
    @PreAuthorize(ADMIN_POINTCUT)
    @UpdateFasstoWarehousingApiDocs
    public ResponseEntity<BaseResponse<UpdateFasstoWarehousingResponse>> updateWarehousing(
            @PathVariable String customerCode,
            @RequestHeader("accessToken") String accessToken,
            @Valid @RequestBody List<UpdateFasstoWarehousingRequest> request
    ) {
        UpdateFasstoWarehousingResult result = updateFasstoWarehousingUseCase.updateWarehousing(
                FasstoWarehousingRequestToCommandMapper.mapToUpdateCommand(customerCode, accessToken, request)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        UpdateFasstoWarehousingResponse.from(result),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "파스토 상품 입고 수정 성공"
                ),
                HttpStatus.OK
        );
    }
}
