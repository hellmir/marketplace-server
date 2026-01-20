package com.personal.marketnote.reward.domain.attendance;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AttendancePolicySnapshotState {
    private final Short id;
    private final short continuousPeriod;
    private final AttendanceRewardType rewardType;
    private final long rewardQuantity;
    private final LocalDateTime attendenceDate;
    private final AttendancePolicyStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
}

