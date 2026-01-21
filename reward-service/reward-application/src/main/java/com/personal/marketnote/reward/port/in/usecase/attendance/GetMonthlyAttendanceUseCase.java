package com.personal.marketnote.reward.port.in.usecase.attendance;

import com.personal.marketnote.reward.port.in.command.attendance.GetMonthlyAttendanceQuery;
import com.personal.marketnote.reward.port.in.result.attendance.GetMonthlyAttendanceResult;

public interface GetMonthlyAttendanceUseCase {
    GetMonthlyAttendanceResult getMonthlyAttendanceHistories(GetMonthlyAttendanceQuery query);
}
