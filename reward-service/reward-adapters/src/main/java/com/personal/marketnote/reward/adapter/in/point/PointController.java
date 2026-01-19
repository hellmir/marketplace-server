package com.personal.marketnote.reward.adapter.in.point;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.utility.ElementExtractor;
import com.personal.marketnote.reward.adapter.in.point.apidocs.GetUserPointApiDocs;
import com.personal.marketnote.reward.adapter.in.point.apidocs.ModifyUserPointApiDocs;
import com.personal.marketnote.reward.adapter.in.point.apidocs.RegisterUserPointApiDocs;
import com.personal.marketnote.reward.adapter.in.point.mapper.PointRequestToCommandMapper;
import com.personal.marketnote.reward.adapter.in.point.request.ModifyUserPointRequest;
import com.personal.marketnote.reward.adapter.in.point.response.GetUserPointReponse;
import com.personal.marketnote.reward.adapter.in.point.response.UpdateUserPointResponse;
import com.personal.marketnote.reward.port.in.command.point.RegisterUserPointCommand;
import com.personal.marketnote.reward.port.in.result.point.GetUserPointResult;
import com.personal.marketnote.reward.port.in.result.point.UpdateUserPointResult;
import com.personal.marketnote.reward.port.in.usecase.point.GetUserPointUseCase;
import com.personal.marketnote.reward.port.in.usecase.point.ModifyUserPointUseCase;
import com.personal.marketnote.reward.port.in.usecase.point.RegisterUserPointUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;
import static com.personal.marketnote.common.utility.ApiConstant.ADMIN_POINTCUT;

@RestController
@RequestMapping("/api/v1/users/{userId}/points")
@Tag(name = "포인트 API", description = "회원 포인트 관련 API")
@RequiredArgsConstructor
public class PointController {
    private final RegisterUserPointUseCase registerUserPointUseCase;
    private final ModifyUserPointUseCase modifyUserPointUseCase;
    private final GetUserPointUseCase getUserPointUseCase;

    /**
     * (관리자) 회원 포인트 정보 생성
     *
     * @param userId 회원 ID
     * @return 회원 포인트 정보 생성 응답 {@link UpdateUserPointResponse}
     * @Author 성효빈
     * @Date 2026-01-17
     * @Description 회원 포인트 정보를 생성합니다.
     */
    @PostMapping
    @PreAuthorize(ADMIN_POINTCUT)
    @RegisterUserPointApiDocs
    public ResponseEntity<BaseResponse<Void>> registerUserPoint(
            @PathVariable("userId") Long userId,
            @RequestParam(name = "userKey") String userKey
    ) {
        registerUserPointUseCase.register(
                RegisterUserPointCommand.of(userId, userKey)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        HttpStatus.CREATED,
                        DEFAULT_SUCCESS_CODE,
                        "회원 포인트 정보 생성 성공"
                ),
                HttpStatus.CREATED
        );
    }

    /**
     * 회원 포인트 정보 조회
     *
     * @param principal 인증된 사용자 정보
     * @return 회원 포인트 정보 조회 응답 {@link GetUserPointReponse}
     * @Author 성효빈
     * @Date 2026-01-18
     * @Description 회원 포인트 정보를 조회합니다.
     */
    @GetMapping
    @GetUserPointApiDocs
    public ResponseEntity<BaseResponse<GetUserPointReponse>> getUserPoint(
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        GetUserPointResult getUserPointResult = GetUserPointResult.from(
                getUserPointUseCase.getUserPoint(
                        ElementExtractor.extractUserId(principal)
                )
        );

        return ResponseEntity.ok(
                BaseResponse.of(
                        GetUserPointReponse.from(getUserPointResult),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "회원 포인트 정보 조회 성공"
                )
        );
    }

    /**
     * (관리자) 회원 포인트 적립/차감
     */
    @PatchMapping
    @PreAuthorize(ADMIN_POINTCUT)
    @ModifyUserPointApiDocs
    public ResponseEntity<BaseResponse<UpdateUserPointResponse>> modifyUserPoint(
            @PathVariable("userId") Long userId,
            @RequestBody @Valid ModifyUserPointRequest request
    ) {
        UpdateUserPointResult result = modifyUserPointUseCase.modify(
                PointRequestToCommandMapper.mapToModifyUserPointCommand(userId, request)
        );

        return ResponseEntity.ok(
                BaseResponse.of(
                        UpdateUserPointResponse.from(result),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "회원 포인트 수정 성공"
                )
        );
    }
}
