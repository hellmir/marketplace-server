package com.personal.marketnote.reward.domain.attendance;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserAttendanceHistoryCreateState {
    private final Long userAttendanceId;
    private final Short attendancePolicyId;
    private final AttendanceRewardType rewardType;
    private final long rewardQuantity;
    private final short continuousPeriod;
    private final Boolean rewardYn;
    private final LocalDateTime attendedAt;
}

