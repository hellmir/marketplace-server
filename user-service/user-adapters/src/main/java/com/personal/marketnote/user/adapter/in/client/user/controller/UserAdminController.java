package com.personal.marketnote.user.adapter.in.client.user.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.user.adapter.in.client.authentication.response.GetUserResponse;
import com.personal.marketnote.user.adapter.in.client.user.controller.apidocs.GetUserInfoApiDocs;
import com.personal.marketnote.user.adapter.in.client.user.controller.apidocs.UpdateUserInfoApiDocs;
import com.personal.marketnote.user.adapter.in.client.user.mapper.UserRequestToCommandMapper;
import com.personal.marketnote.user.adapter.in.client.user.request.UpdateUserInfoRequest;
import com.personal.marketnote.user.port.in.usecase.user.GetUserUseCase;
import com.personal.marketnote.user.port.in.usecase.user.UpdateUserUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;
import static com.personal.marketnote.common.utility.ApiConstant.ADMIN_POINTCUT;

/**
 * 회원 관리자 컨트롤러
 *
 * @Author 성효빈
 * @Date 2025-12-29
 * @Description 회원 관련 관리자 API를 제공합니다.
 */
@RestController
@RequestMapping("/api/v1/admin/users")
@Tag(
        name = "회원 관리자 API",
        description = "회원 관련 관리자 API"
)
@RequiredArgsConstructor
@Slf4j
public class UserAdminController {
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    /**
     * (관리자) 회원 정보 조회
     *
     * @param id 회원 ID
     * @return 회원 정보 조회 응답 {@link GetUserResponse}
     * @Author 성효빈
     * @Date 2025-12-29
     * @Description 회원 정보를 조회합니다. 관리자만 가능합니다.
     */
    @GetMapping("/{id}")
    @PreAuthorize(ADMIN_POINTCUT)
    @GetUserInfoApiDocs
    public ResponseEntity<BaseResponse<GetUserResponse>> getUserInfo(
            @PathVariable Long id
    ) {
        GetUserResponse getUserResponse = GetUserResponse.from(
                getUserUseCase.getAllStatusUserInfo(id)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        getUserResponse,
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "회원 정보 조회 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * (관리자) 회원 정보 수정
     *
     * @param updateUserInfoRequest 회원 정보 수정 요청
     * @return 회원 정보 수정 응답 {@link Void}
     * @Author 성효빈
     * @Date 2025-12-29
     * @Description 계정 비활성화를 포함한 회원 정보를 수정합니다. 관리자만 가능합니다.
     */
    @PatchMapping("/{id}")
    @PreAuthorize(ADMIN_POINTCUT)
    @UpdateUserInfoApiDocs
    public ResponseEntity<BaseResponse<Void>> updateUserInfo(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserInfoRequest updateUserInfoRequest
    ) {
        updateUserUseCase.updateUserInfo(
                true, id, UserRequestToCommandMapper.mapToCommand(updateUserInfoRequest)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        null,
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "회원 정보 수정 성공"
                ),
                HttpStatus.OK
        );
    }
}
