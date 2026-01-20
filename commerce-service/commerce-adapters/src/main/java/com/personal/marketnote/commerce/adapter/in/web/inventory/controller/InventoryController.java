package com.personal.marketnote.commerce.adapter.in.web.inventory.controller;

import com.personal.marketnote.commerce.adapter.in.web.inventory.controller.apidocs.GetInventoriesApiDocs;
import com.personal.marketnote.commerce.adapter.in.web.inventory.controller.apidocs.RegisterInventoryApiDocs;
import com.personal.marketnote.commerce.adapter.in.web.inventory.mapper.InventoryRequestToCommandMapper;
import com.personal.marketnote.commerce.adapter.in.web.inventory.response.GetInventoriesResponse;
import com.personal.marketnote.commerce.port.in.result.inventory.GetInventoriesResult;
import com.personal.marketnote.commerce.port.in.usecase.inventory.GetInventoryUseCase;
import com.personal.marketnote.commerce.port.in.usecase.inventory.RegisterInventoryUseCase;
import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.adapter.in.request.RegisterInventoryRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;
import static com.personal.marketnote.common.utility.ApiConstant.ADMIN_OR_SELLER_POINTCUT;
import static com.personal.marketnote.common.utility.ApiConstant.ADMIN_POINTCUT;

@RestController
@RequestMapping("/api/v1/inventories")
@Tag(name = "재고 API", description = "재고 관련 API")
@RequiredArgsConstructor
public class InventoryController {
    private final RegisterInventoryUseCase registerInventoryUseCase;
    private final GetInventoryUseCase getInventoryUseCase;

    /**
     * 재고 도메인 등록
     *
     * @param request 재고 도메인 등록 요청
     * @Author 성효빈
     * @Date 2026-01-06
     * @Description 재고 도메인을 등록합니다.
     */
    @PostMapping
    @PreAuthorize(ADMIN_POINTCUT)
    @RegisterInventoryApiDocs
    public ResponseEntity<BaseResponse<Void>> registerInventory(
            @Valid @RequestBody RegisterInventoryRequest request
    ) {
        registerInventoryUseCase.registerInventory(
                InventoryRequestToCommandMapper.mapToCommand(request)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        null,
                        HttpStatus.CREATED,
                        DEFAULT_SUCCESS_CODE,
                        "재고 도메인 등록 성공"
                ),
                HttpStatus.CREATED
        );
    }

    /**
     * 상품 재고 목록 조회
     *
     * @param pricePolicyIds 가격 정책 ID 목록
     * @Author 성효빈
     * @Date 2026-01-07
     * @Description 상품 재고 목록을 조회합니다.
     */
    @GetMapping
    @PreAuthorize(ADMIN_OR_SELLER_POINTCUT)
    @GetInventoriesApiDocs
    public ResponseEntity<BaseResponse<GetInventoriesResponse>> getInventories(
            @Valid @RequestParam List<Long> pricePolicyIds
    ) {
        GetInventoriesResult getInventoriesResult
                = GetInventoriesResult.from(getInventoryUseCase.getInventories(pricePolicyIds));

        return new ResponseEntity<>(
                BaseResponse.of(
                        GetInventoriesResponse.from(getInventoriesResult),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "상품 재고 목록 조회 성공"
                ),
                HttpStatus.OK
        );
    }
}
