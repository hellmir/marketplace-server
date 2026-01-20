package com.personal.marketnote.reward.port.out.attendance;

import com.personal.marketnote.reward.domain.attendance.UserAttendanceHistory;

import java.util.Optional;

public interface FindUserAttendanceHistoryPort {
    Optional<UserAttendanceHistory> findLatestByUserId(Long userId);
}

