package com.personal.marketnote.community.port.out.like;

import com.personal.marketnote.community.domain.like.Like;

public interface SaveLikePort {
    void save(Like like);
}
