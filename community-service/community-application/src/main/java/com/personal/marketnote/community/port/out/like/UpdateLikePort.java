package com.personal.marketnote.community.port.out.like;

import com.personal.marketnote.community.domain.like.Like;
import com.personal.marketnote.community.exception.LikeNotFoundException;

public interface UpdateLikePort {
    void update(Like like) throws LikeNotFoundException;
}
