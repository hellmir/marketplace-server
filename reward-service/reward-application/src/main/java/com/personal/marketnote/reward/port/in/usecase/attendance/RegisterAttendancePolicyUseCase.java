package com.personal.marketnote.reward.port.in.usecase.attendance;

import com.personal.marketnote.reward.port.in.command.attendance.RegisterAttendancePolicyCommand;
import com.personal.marketnote.reward.port.in.result.attendance.RegisterAttendancePolicyResult;

public interface RegisterAttendancePolicyUseCase {
    RegisterAttendancePolicyResult register(RegisterAttendancePolicyCommand command);
}

