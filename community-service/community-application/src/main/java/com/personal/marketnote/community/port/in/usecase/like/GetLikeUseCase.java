package com.personal.marketnote.community.port.in.usecase.like;

import com.personal.marketnote.community.domain.like.Like;
import com.personal.marketnote.community.domain.like.LikeTargetType;

public interface GetLikeUseCase {
    Like getLike(LikeTargetType targetType, Long targetId, Long userId);
}
