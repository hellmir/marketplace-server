package com.personal.marketnote.community.port.in.command.like;

import com.personal.marketnote.community.domain.like.LikeTargetType;

public record RegisterLikeCommand(
        LikeTargetType targetType,
        Long targetId,
        Long userId
) {
    public static RegisterLikeCommand of(LikeTargetType targetType, Long targetId, Long userId) {
        return new RegisterLikeCommand(targetType, targetId, userId);
    }
}
