package com.personal.marketnote.reward.port.out.attendance;

import com.personal.marketnote.reward.domain.attendance.UserAttendanceHistory;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FindUserAttendanceHistoryPort {
    Optional<UserAttendanceHistory> findLatestByUserAttendanceId(Long userAttendanceId);

    boolean existsByUserAttendanceIdAndAttendedAtBetween(Long userAttendanceId, LocalDateTime startInclusive, LocalDateTime endExclusive);
}

