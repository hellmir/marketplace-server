package com.personal.marketnote.community.port.out.like;

import com.personal.marketnote.community.domain.like.Like;
import com.personal.marketnote.community.exception.LikeNotFoundException;

public interface UpdateLikePort {
    /**
     * @param like 좋아요
     * @throws LikeNotFoundException 좋아요 업데이트 실패 시 예외
     * @Date 2026-01-16
     * @Author 성효빈
     * @Description 좋아요를 업데이트합니다.
     */
    void update(Like like) throws LikeNotFoundException;
}
