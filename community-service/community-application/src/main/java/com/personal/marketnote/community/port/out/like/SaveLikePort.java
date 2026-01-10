package com.personal.marketnote.community.port.out.like;

import com.personal.marketnote.community.domain.like.Like;

public interface SaveLikePort {
    Like save(Like like);

    boolean existsByTarget(com.personal.marketnote.community.domain.like.LikeTargetType targetType, Long targetId, Long userId);
}
