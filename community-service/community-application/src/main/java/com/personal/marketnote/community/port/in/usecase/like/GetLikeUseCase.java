package com.personal.marketnote.community.port.in.usecase.like;

import com.personal.marketnote.community.domain.like.Like;
import com.personal.marketnote.community.domain.like.LikeTargetType;

public interface GetLikeUseCase {
    boolean existsUserLike(LikeTargetType targetType, Long targetId, Long userId);

    Like getLike(LikeTargetType targetType, Long targetId, Long userId);
}
