package com.personal.marketnote.reward.port.out.attendance;

import com.personal.marketnote.reward.domain.attendance.UserAttendance;

public interface SaveUserAttendancePort {
    UserAttendance save(UserAttendance attendance);
}

