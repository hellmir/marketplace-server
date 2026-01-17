package com.personal.marketnote.reward.port.in.command.point;

public record RegisterUserPointCommand(Long userId) {
    public static RegisterUserPointCommand of(Long userId) {
        return new RegisterUserPointCommand(userId);
    }
}
