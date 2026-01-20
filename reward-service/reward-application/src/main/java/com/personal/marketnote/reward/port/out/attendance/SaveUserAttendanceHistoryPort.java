package com.personal.marketnote.reward.port.out.attendance;

import com.personal.marketnote.reward.domain.attendance.UserAttendanceHistory;

public interface SaveUserAttendanceHistoryPort {
    UserAttendanceHistory save(UserAttendanceHistory history);
}

