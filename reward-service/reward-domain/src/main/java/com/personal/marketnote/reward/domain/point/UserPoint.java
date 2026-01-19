package com.personal.marketnote.reward.domain.point;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class UserPoint {
    private Long userId;
    private String userKey;
    private PointAmount amount;
    private Long addExpectedAmount;
    private Long expireExpectedAmount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public UserPoint withAmount(Long amount) {
        return UserPoint.builder()
                .userId(userId)
                .userKey(userKey)
                .amount(PointAmount.of(String.valueOf(amount)))
                .addExpectedAmount(addExpectedAmount)
                .expireExpectedAmount(expireExpectedAmount)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }

    public static UserPoint from(UserPointCreateState state) {
        return UserPoint.builder()
                .userId(state.getUserId())
                .userKey(state.getUserKey())
                .amount(PointAmount.of(String.valueOf(state.getAmount())))
                .addExpectedAmount(state.getAddExpectedAmount())
                .expireExpectedAmount(state.getExpireExpectedAmount())
                .build();
    }

    public static UserPoint from(UserPointSnapshotState state) {
        return UserPoint.builder()
                .userId(state.getUserId())
                .amount(PointAmount.of(String.valueOf(state.getAmount())))
                .addExpectedAmount(state.getAddExpectedAmount())
                .expireExpectedAmount(state.getExpireExpectedAmount())
                .createdAt(state.getCreatedAt())
                .modifiedAt(state.getModifiedAt())
                .build();
    }

    public void changeAmount(boolean isAccrual, Long amount) {
        this.amount = PointAmount.generateChangedAmount(isAccrual, this.amount, amount);
    }

    public Long getAmountValue() {
        return amount.getValue();
    }
}
