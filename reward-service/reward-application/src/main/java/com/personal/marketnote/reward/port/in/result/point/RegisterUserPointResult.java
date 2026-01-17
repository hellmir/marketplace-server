package com.personal.marketnote.reward.port.in.result.point;

import com.personal.marketnote.reward.domain.point.UserPoint;

import java.time.LocalDateTime;

public record RegisterUserPointResult(
        Long userId,
        Long amount,
        Long addExpectedAmount,
        Long expireExpectedAmount,
        LocalDateTime createdAt
) {
    public static RegisterUserPointResult from(UserPoint userPoint) {
        return new RegisterUserPointResult(
                userPoint.getUserId(),
                userPoint.getAmount(),
                userPoint.getAddExpectedAmount(),
                userPoint.getExpireExpectedAmount(),
                userPoint.getCreatedAt()
        );
    }
}
