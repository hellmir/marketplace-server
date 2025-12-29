package com.personal.marketnote.user.adapter.in.client.user.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.user.adapter.in.client.user.controller.apidocs.GetUserInfoApiDocs;
import com.personal.marketnote.user.adapter.in.client.user.controller.apidocs.GetUsersApiDocs;
import com.personal.marketnote.user.adapter.in.client.user.controller.apidocs.UpdateUserInfoApiDocs;
import com.personal.marketnote.user.adapter.in.client.user.mapper.UserRequestToCommandMapper;
import com.personal.marketnote.user.adapter.in.client.user.request.UpdateUserInfoRequest;
import com.personal.marketnote.user.adapter.in.client.user.response.GetUserInfoResponse;
import com.personal.marketnote.user.adapter.in.client.user.response.GetUsersResponse;
import com.personal.marketnote.user.domain.user.SearchTarget;
import com.personal.marketnote.user.domain.user.SortProperty;
import com.personal.marketnote.user.port.in.usecase.user.GetUserUseCase;
import com.personal.marketnote.user.port.in.usecase.user.UpdateUserUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;
import static com.personal.marketnote.common.utility.ApiConstant.ADMIN_POINTCUT;
import static com.personal.marketnote.common.utility.ApiConstant.DEFAULT_PAGE_NUMBER;

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
    private static final String GET_USERS_DEFAULT_PAGE_SIZE = "10";

    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    /**
     * (관리자) 회원 목록 조회
     *
     * @param pageSize      페이지 크기
     * @param pageNumber    페이지 번호
     * @param sortDirection 정렬 방향
     * @param sortProperty  정렬 속성
     * @param searchTarget  검색 대상
     * @param searchKeyword 검색 키워드
     * @return 회원 목록 조회 응답 {@link GetUsersResponse}
     * @Author 성효빈
     * @Date 2025-12-29
     * @Description 회원 정보를 조회합니다. 관리자만 가능합니다.
     */
    @GetMapping
    @PreAuthorize(ADMIN_POINTCUT)
    @GetUsersApiDocs
    public ResponseEntity<BaseResponse<GetUsersResponse>> getUsers(
            @RequestParam(required = false, defaultValue = GET_USERS_DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection,
            @RequestParam(required = false, defaultValue = "EMAIL") SortProperty sortProperty,
            @RequestParam(required = false, defaultValue = "EMAIL") SearchTarget searchTarget,
            @RequestParam(required = false) String searchKeyword
    ) {
        GetUsersResponse getUsersResponse = GetUsersResponse.from(
                getUserUseCase.getAllStatusUsers(
                        pageSize,
                        pageNumber - 1,
                        sortDirection,
                        sortProperty,
                        searchTarget,
                        searchKeyword
                )
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        getUsersResponse,
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "회원 목록 조회 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * (관리자) 회원 정보 조회
     *
     * @param id 회원 ID
     * @return 회원 정보 조회 응답 {@link GetUserInfoResponse}
     * @Author 성효빈
     * @Date 2025-12-29
     * @Description 회원 정보를 조회합니다. 관리자만 가능합니다.
     */
    @GetMapping("/{id}")
    @PreAuthorize(ADMIN_POINTCUT)
    @GetUserInfoApiDocs
    public ResponseEntity<BaseResponse<GetUserInfoResponse>> getUserInfo(
            @PathVariable Long id
    ) {
        GetUserInfoResponse getUserInfoResponse = GetUserInfoResponse.from(
                getUserUseCase.getAllStatusUserInfo(id)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        getUserInfoResponse,
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
