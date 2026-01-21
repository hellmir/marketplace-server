package com.personal.marketnote.reward.adapter.in.web.attendance;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.reward.adapter.in.web.attendance.apidocs.RegisterAttendancePolicyApiDocs;
import com.personal.marketnote.reward.adapter.in.web.attendance.mapper.AttendanceRequestToCommandMapper;
import com.personal.marketnote.reward.adapter.in.web.attendance.request.RegisterAttendancePolicyRequest;
import com.personal.marketnote.reward.adapter.in.web.attendance.response.RegisterAttendancePolicyResponse;
import com.personal.marketnote.reward.port.in.result.attendance.RegisterAttendancePolicyResult;
import com.personal.marketnote.reward.port.in.usecase.attendance.RegisterAttendancePolicyUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;
import static com.personal.marketnote.common.utility.ApiConstant.ADMIN_POINTCUT;

@RestController
@RequestMapping("/api/v1/attendance")
@Tag(name = "출석 정책 API", description = "출석 정책 관련 API")
@RequiredArgsConstructor
public class AttendancePolicyController {
    private final RegisterAttendancePolicyUseCase registerAttendancePolicyUseCase;

    /**
     * (관리자) 출석 정책 등록
     *
     * @param request 출석 정책 등록 요청
     * @return 출석 정책 등록 응답
     * @Author 성효빈
     * @Date 2026-01-21
     * @Description 출석 정책을 등록합니다.
     */
    @PostMapping("/policies")
    @PreAuthorize(ADMIN_POINTCUT)
    @RegisterAttendancePolicyApiDocs
    public ResponseEntity<BaseResponse<RegisterAttendancePolicyResponse>> registerAttendancePolicy(
            @Valid @RequestBody RegisterAttendancePolicyRequest request
    ) {
        RegisterAttendancePolicyResult result = registerAttendancePolicyUseCase.register(
                AttendanceRequestToCommandMapper.mapToCommand(request)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        RegisterAttendancePolicyResponse.from(result),
                        HttpStatus.CREATED,
                        DEFAULT_SUCCESS_CODE,
                        "출석 정책 등록 성공"
                ),
                HttpStatus.CREATED
        );
    }
}
