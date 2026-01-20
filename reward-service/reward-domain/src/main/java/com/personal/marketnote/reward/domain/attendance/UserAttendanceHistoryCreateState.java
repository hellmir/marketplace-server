package com.personal.marketnote.reward.domain.attendance;

import com.personal.marketnote.common.domain.calendar.Month;
import com.personal.marketnote.common.domain.calendar.Year;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserAttendanceHistoryCreateState {
    private final Long userId;
    private final Short attendancePolicyId;
    private final Year year;
    private final Month month;
    private final AttendanceRewardType rewardType;
    private final long rewardQuantity;
    private final short continuousPeriod;
    private final Boolean rewardYn;
    private final LocalDateTime attendedAt;
}

