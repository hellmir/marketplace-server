package com.personal.marketnote.community.adapter.out.persistence.like;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.community.adapter.out.mapper.LikeJpaEntityToDomainMapper;
import com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity;
import com.personal.marketnote.community.adapter.out.persistence.like.repository.LikeJpaRepository;
import com.personal.marketnote.community.domain.like.Like;
import com.personal.marketnote.community.port.out.like.SaveLikePort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class LikePersistenceAdapter implements SaveLikePort {
    private final LikeJpaRepository likeJpaRepository;

    @Override
    public Like save(Like like) {
        LikeJpaEntity saved = likeJpaRepository.save(LikeJpaEntity.from(like));
        return LikeJpaEntityToDomainMapper.mapToDomain(saved).orElse(null);
    }

    @Override
    public boolean existsByTarget(com.personal.marketnote.community.domain.like.LikeTargetType targetType, Long targetId, Long userId) {
        return likeJpaRepository.existsByTarget(targetType, targetId, userId);
    }
}
