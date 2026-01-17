package com.personal.marketnote.reward.adapter.in.point.response;

import com.personal.marketnote.reward.port.in.result.point.RegisterUserPointResult;

import java.time.LocalDateTime;

public record RegisterUserPointResponse(
        Long userId,
        Long amount,
        Long addExpectedAmount,
        Long expireExpectedAmount,
        LocalDateTime createdAt
) {
    public static RegisterUserPointResponse from(RegisterUserPointResult result) {
        return new RegisterUserPointResponse(
                result.userId(),
                result.amount(),
                result.addExpectedAmount(),
                result.expireExpectedAmount(),
                result.createdAt()
        );
    }
}
