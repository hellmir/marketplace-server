package com.personal.marketnote.reward.adapter.out.persistence.attendance.entity;

import com.personal.marketnote.common.adapter.out.persistence.audit.BaseEntity;
import com.personal.marketnote.common.domain.calendar.Month;
import com.personal.marketnote.common.domain.calendar.Year;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.reward.domain.attendance.AttendanceRewardType;
import com.personal.marketnote.reward.domain.attendance.UserAttendanceHistory;
import com.personal.marketnote.reward.domain.attendance.UserAttendanceHistorySnapshotState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_attendance_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class UserAttendanceHistoryJpaEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "attendance_policy_id")
    private Short attendancePolicyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "year", nullable = false, length = 7)
    private Year year;

    @Enumerated(EnumType.STRING)
    @Column(name = "month", nullable = false, length = 15)
    private Month month;

    @Enumerated(EnumType.STRING)
    @Column(name = "reward_type", nullable = false, length = 31)
    private AttendanceRewardType rewardType;

    @Column(name = "reward_quantity", nullable = false)
    private long rewardQuantity;

    @Column(name = "continuous_period", nullable = false)
    private short continuousPeriod;

    @Column(name = "reward_yn", nullable = false)
    private Boolean rewardYn;

    @Column(name = "attended_at", nullable = false)
    private LocalDateTime attendedAt;

    public static UserAttendanceHistoryJpaEntity from(UserAttendanceHistory history) {
        if (FormatValidator.hasNoValue(history)) {
            return null;
        }

        return UserAttendanceHistoryJpaEntity.builder()
                .id(history.getId())
                .userId(history.getUserId())
                .attendancePolicyId(history.getAttendancePolicyId())
                .year(history.getYear())
                .month(history.getMonth())
                .rewardType(history.getRewardType())
                .rewardQuantity(history.getRewardQuantity())
                .continuousPeriod(history.getContinuousPeriod())
                .rewardYn(history.getRewardYn())
                .attendedAt(history.getAttendedAt())
                .build();
    }

    public UserAttendanceHistory toDomain() {
        return UserAttendanceHistory.from(
                UserAttendanceHistorySnapshotState.builder()
                        .id(id)
                        .userId(userId)
                        .attendancePolicyId(attendancePolicyId)
                        .year(year)
                        .month(month)
                        .rewardType(rewardType)
                        .rewardQuantity(rewardQuantity)
                        .continuousPeriod(continuousPeriod)
                        .rewardYn(rewardYn)
                        .attendedAt(attendedAt)
                        .build()
        );
    }
}

