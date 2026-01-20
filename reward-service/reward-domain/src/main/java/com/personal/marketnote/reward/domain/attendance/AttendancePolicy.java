package com.personal.marketnote.reward.domain.attendance;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class AttendancePolicy {
    private Short id;
    private short continuousPeriod;
    private AttendanceRewardType rewardType;
    private long rewardQuantity;
    private LocalDateTime attendenceDate;
    private AttendancePolicyStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static AttendancePolicy from(AttendancePolicySnapshotState state) {
        return AttendancePolicy.builder()
                .id(state.getId())
                .continuousPeriod(state.getContinuousPeriod())
                .rewardType(state.getRewardType())
                .rewardQuantity(state.getRewardQuantity())
                .attendenceDate(state.getAttendenceDate())
                .status(state.getStatus())
                .createdAt(state.getCreatedAt())
                .modifiedAt(state.getModifiedAt())
                .build();
    }
}

