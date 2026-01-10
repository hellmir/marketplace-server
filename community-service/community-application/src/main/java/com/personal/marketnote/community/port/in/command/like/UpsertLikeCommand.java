package com.personal.marketnote.community.port.in.command.like;

import com.personal.marketnote.community.domain.like.LikeTargetType;

public record UpsertLikeCommand(
        LikeTargetType targetType,
        Long targetId,
        Long userId
) {
    public static UpsertLikeCommand of(LikeTargetType targetType, Long targetId, Long userId) {
        return new UpsertLikeCommand(targetType, targetId, userId);
    }
}
