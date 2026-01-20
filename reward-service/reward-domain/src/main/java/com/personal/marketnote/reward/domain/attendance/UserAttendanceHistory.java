package com.personal.marketnote.reward.domain.attendance;

import com.personal.marketnote.common.domain.calendar.Month;
import com.personal.marketnote.common.domain.calendar.Year;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class UserAttendanceHistory {
    private Long id;
    private Long userId;
    private Short attendancePolicyId;
    private Year year;
    private Month month;
    private AttendanceRewardType rewardType;
    private long rewardQuantity;
    private short continuousPeriod;
    private Boolean rewardYn;
    private LocalDateTime attendedAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static UserAttendanceHistory from(UserAttendanceHistoryCreateState state) {
        return UserAttendanceHistory.builder()
                .userId(state.getUserId())
                .attendancePolicyId(state.getAttendancePolicyId())
                .year(state.getYear())
                .month(state.getMonth())
                .rewardType(state.getRewardType())
                .rewardQuantity(state.getRewardQuantity())
                .continuousPeriod(state.getContinuousPeriod())
                .rewardYn(state.getRewardYn())
                .attendedAt(state.getAttendedAt())
                .build();
    }

    public static UserAttendanceHistory from(UserAttendanceHistorySnapshotState state) {
        return UserAttendanceHistory.builder()
                .id(state.getId())
                .userId(state.getUserId())
                .attendancePolicyId(state.getAttendancePolicyId())
                .year(state.getYear())
                .month(state.getMonth())
                .rewardType(state.getRewardType())
                .rewardQuantity(state.getRewardQuantity())
                .continuousPeriod(state.getContinuousPeriod())
                .rewardYn(state.getRewardYn())
                .attendedAt(state.getAttendedAt())
                .createdAt(state.getCreatedAt())
                .modifiedAt(state.getModifiedAt())
                .build();
    }
}

