package com.personal.marketnote.reward.adapter.in.web.attendance;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.utility.ElementExtractor;
import com.personal.marketnote.reward.adapter.in.web.attendance.apidocs.RegisterAttendanceApiDocs;
import com.personal.marketnote.reward.adapter.in.web.attendance.mapper.AttendanceRequestToCommandMapper;
import com.personal.marketnote.reward.adapter.in.web.attendance.request.RegisterAttendanceRequest;
import com.personal.marketnote.reward.adapter.in.web.attendance.response.RegisterAttendanceResponse;
import com.personal.marketnote.reward.port.in.result.attendance.RegisterAttendanceResult;
import com.personal.marketnote.reward.port.in.usecase.attendance.RegisterAttendanceUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;

@RestController
@RequestMapping("/api/v1/attendance")
@Tag(name = "출석체크 API", description = "출석체크 관련 API")
@RequiredArgsConstructor
public class AttendanceController {
    private final RegisterAttendanceUseCase registerAttendanceUseCase;

    /**
     * 회원 출석체크 등록
     *
     * @param principal 인증된 사용자 정보
     * @param request   출석 요청 본문
     * @return 출석체크 등록 응답
     * @Author 성효빈
     * @Date 2026-01-20
     * @Description 클라이언트 출석 요청 시간을 검증하고, 정책을 적용하여 출석 기록을 생성합니다.
     */
    @PostMapping
    @RegisterAttendanceApiDocs
    public ResponseEntity<BaseResponse<RegisterAttendanceResponse>> registerAttendance(
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal,
            @Valid @RequestBody RegisterAttendanceRequest request
    ) {
        RegisterAttendanceResult result = registerAttendanceUseCase.register(
                AttendanceRequestToCommandMapper.mapToCommand(
                        ElementExtractor.extractUserId(principal), request
                )
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        RegisterAttendanceResponse.from(result),
                        HttpStatus.CREATED,
                        DEFAULT_SUCCESS_CODE,
                        "회원 출석체크 등록 성공"
                ),
                HttpStatus.CREATED
        );
    }
}
