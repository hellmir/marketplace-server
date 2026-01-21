package com.personal.marketnote.reward.adapter.out.persistence.attendance.entity;

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
public class UserAttendanceHistoryJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_attendance_id", nullable = false)
    private Long userAttendanceId;

    @Column(name = "attendance_policy_id")
    private Short attendancePolicyId;

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

    @org.hibernate.annotations.CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    public static UserAttendanceHistoryJpaEntity from(UserAttendanceHistory history) {
        if (FormatValidator.hasNoValue(history)) {
            return null;
        }

        return UserAttendanceHistoryJpaEntity.builder()
                .id(history.getId())
                .userAttendanceId(history.getUserAttendanceId())
                .attendancePolicyId(history.getAttendancePolicyId())
                .rewardType(history.getRewardType())
                .rewardQuantity(history.getRewardQuantity())
                .continuousPeriod(history.getContinuousPeriod())
                .rewardYn(history.getRewardYn())
                .attendedAt(history.getAttendedAt())
                .createdAt(history.getCreatedAt())
                .modifiedAt(history.getModifiedAt())
                .build();
    }

    public UserAttendanceHistory toDomain() {
        return UserAttendanceHistory.from(
                UserAttendanceHistorySnapshotState.builder()
                        .id(id)
                        .userAttendanceId(userAttendanceId)
                        .attendancePolicyId(attendancePolicyId)
                        .rewardType(rewardType)
                        .rewardQuantity(rewardQuantity)
                        .continuousPeriod(continuousPeriod)
                        .rewardYn(rewardYn)
                        .attendedAt(attendedAt)
                        .createdAt(createdAt)
                        .modifiedAt(modifiedAt)
                        .build()
        );
    }
}

