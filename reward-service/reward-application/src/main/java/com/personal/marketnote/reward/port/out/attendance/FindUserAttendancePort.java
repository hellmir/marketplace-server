package com.personal.marketnote.reward.port.out.attendance;

import com.personal.marketnote.common.domain.calendar.Month;
import com.personal.marketnote.common.domain.calendar.Year;
import com.personal.marketnote.reward.domain.attendance.UserAttendance;

import java.util.Optional;

public interface FindUserAttendancePort {
    Optional<UserAttendance> findByUserIdAndYearAndMonth(Long userId, Year year, Month month);
}

