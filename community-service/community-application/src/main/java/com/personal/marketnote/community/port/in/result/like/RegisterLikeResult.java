package com.personal.marketnote.community.port.in.result.like;

import com.personal.marketnote.community.domain.like.Like;
import com.personal.marketnote.community.domain.like.LikeTargetType;

public record RegisterLikeResult(
        LikeTargetType targetType,
        Long targetId,
        Long userId
) {
    public static RegisterLikeResult from(Like like) {
        return new RegisterLikeResult(
                like.getTargetType(),
                like.getTargetId(),
                like.getUserId()
        );
    }
}
