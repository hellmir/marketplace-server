package com.personal.marketnote.reward.adapter.out.persistence.point.entity;

import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.reward.domain.point.UserPoint;
import com.personal.marketnote.reward.domain.point.UserPointSnapshotState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_point")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class UserPointJpaEntity {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "add_expected_amount", nullable = false)
    private Long addExpectedAmount;

    @Column(name = "expire_expected_amount", nullable = false)
    private Long expireExpectedAmount;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    public static UserPointJpaEntity from(UserPoint userPoint) {
        if (!FormatValidator.hasValue(userPoint)) {
            return null;
        }

        return UserPointJpaEntity.builder()
                .userId(userPoint.getUserId())
                .amount(userPoint.getAmountValue())
                .addExpectedAmount(userPoint.getAddExpectedAmount())
                .expireExpectedAmount(userPoint.getExpireExpectedAmount())
                .createdAt(userPoint.getCreatedAt())
                .modifiedAt(userPoint.getModifiedAt())
                .build();
    }

    public UserPoint toDomain() {
        return UserPoint.from(
                UserPointSnapshotState.builder()
                        .userId(userId)
                        .amount(amount)
                        .addExpectedAmount(addExpectedAmount)
                        .expireExpectedAmount(expireExpectedAmount)
                        .createdAt(createdAt)
                        .modifiedAt(modifiedAt)
                        .build()
        );
    }

    public void updateFrom(UserPoint userPoint) {
        this.amount = userPoint.getAmountValue();
        this.addExpectedAmount = userPoint.getAddExpectedAmount();
        this.expireExpectedAmount = userPoint.getExpireExpectedAmount();
    }
}
