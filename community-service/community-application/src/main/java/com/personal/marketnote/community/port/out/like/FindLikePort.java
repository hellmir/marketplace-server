package com.personal.marketnote.community.port.out.like;

import com.personal.marketnote.community.domain.like.Like;
import com.personal.marketnote.community.domain.like.LikeTargetType;
import com.personal.marketnote.community.exception.LikeNotFoundException;

import java.util.Optional;

public interface FindLikePort {
    boolean existsByTarget(LikeTargetType targetType, Long targetId, Long userId);

    Optional<Like> findByTargetAndUser(LikeTargetType targetType, Long targetId, Long userId)
            throws LikeNotFoundException;
}
