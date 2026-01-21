package com.personal.marketnote.reward.port.out.attendance;

import com.personal.marketnote.reward.domain.attendance.AttendancePolicy;

public interface UpdateAttendancePolicyPort {
    void update(AttendancePolicy attendancePolicy);
}
