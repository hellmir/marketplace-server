package com.personal.marketnote.reward.adapter.in.web.attendance.response;

import com.personal.marketnote.reward.port.in.result.attendance.RegisterAttendanceResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterAttendanceResponse {
    private final Long id;

    public static RegisterAttendanceResponse from(RegisterAttendanceResult result) {
        return RegisterAttendanceResponse.builder()
                .id(result.getId())
                .build();
    }
}

