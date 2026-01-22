package com.personal.marketnote.reward.adapter.out.persistence.attendance.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.personal.marketnote.common.domain.calendar.Month;
import com.personal.marketnote.common.domain.calendar.Year;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.reward.domain.attendance.UserAttendance;
import com.personal.marketnote.reward.domain.attendance.UserAttendanceSnapshotState;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_attendance")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class UserAttendanceJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Key\"")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "year", length = 7)
    private Year year;

    @Enumerated(EnumType.STRING)
    @Column(name = "month", length = 15)
    private Month month;

    @Column(name = "total_reward_quantity", nullable = false)
    private long totalRewardQuantity;

    @OneToMany(mappedBy = "userAttendanceId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserAttendanceHistoryJpaEntity> histories;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    public static UserAttendanceJpaEntity from(UserAttendance attendance) {
        if (FormatValidator.hasNoValue(attendance)) {
            return null;
        }

        return UserAttendanceJpaEntity.builder()
                .id(attendance.getId())
                .userId(attendance.getUserId())
                .year(attendance.getYear())
                .month(attendance.getMonth())
                .createdAt(attendance.getCreatedAt())
                .totalRewardQuantity(attendance.getTotalRewardQuantity())
                .histories(attendance.getHistories() == null ? null : attendance.getHistories().stream()
                        .map(UserAttendanceHistoryJpaEntity::from)
                        .toList())
                .build();
    }

    public UserAttendance toDomain() {
        return UserAttendance.from(
                UserAttendanceSnapshotState.builder()
                        .id(id)
                        .userId(userId)
                        .year(year)
                        .month(month)
                        .createdAt(createdAt)
                        .totalRewardQuantity(totalRewardQuantity)
                        .histories(histories == null ? null : histories.stream()
                                .map(UserAttendanceHistoryJpaEntity::toDomain)
                                .toList())
                        .build()
        );
    }
}

