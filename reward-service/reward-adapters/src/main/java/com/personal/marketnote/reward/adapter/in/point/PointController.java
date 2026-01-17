package com.personal.marketnote.reward.adapter.in.point;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.reward.adapter.in.point.apidocs.RegisterUserPointApiDocs;
import com.personal.marketnote.reward.adapter.in.point.response.RegisterUserPointResponse;
import com.personal.marketnote.reward.port.in.command.point.RegisterUserPointCommand;
import com.personal.marketnote.reward.port.in.usecase.point.RegisterUserPointUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;
import static com.personal.marketnote.common.utility.ApiConstant.ADMIN_POINTCUT;

@RestController
@RequestMapping("/api/v1/users/{userId}/points")
@Tag(name = "포인트 API", description = "회원 포인트 관련 API")
@RequiredArgsConstructor
public class PointController {
    private final RegisterUserPointUseCase registerUserPointUseCase;

    /**
     * (관리자) 회원 포인트 정보 생성
     *
     * @param userId 회원 ID
     * @return 회원 포인트 정보 생성 응답 {@link RegisterUserPointResponse}
     * @Author 성효빈
     * @Date 2026-01-17
     * @Description 회원 포인트 정보를 생성합니다.
     */
    @PostMapping
    @PreAuthorize(ADMIN_POINTCUT)
    @RegisterUserPointApiDocs
    public ResponseEntity<BaseResponse<Void>> registerUserPoint(
            @PathVariable("userId") Long userId
    ) {
        registerUserPointUseCase.register(
                RegisterUserPointCommand.of(userId)
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
}
