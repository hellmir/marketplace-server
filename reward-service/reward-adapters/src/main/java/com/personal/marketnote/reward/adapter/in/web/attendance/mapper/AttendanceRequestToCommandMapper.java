package com.personal.marketnote.reward.adapter.in.web.attendance.mapper;

import com.personal.marketnote.reward.adapter.in.web.attendance.request.RegisterAttendanceRequest;
import com.personal.marketnote.reward.port.in.command.attendance.RegisterAttendanceCommand;

public class AttendanceRequestToCommandMapper {
    public static RegisterAttendanceCommand mapToCommand(Long userId, RegisterAttendanceRequest request) {
        return RegisterAttendanceCommand.builder()
                .userId(userId)
                .attendedAt(request.getAttendedAt())
                .build();
    }
}

