package com.personal.marketnote.community.adapter.out.mapper;

import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity;
import com.personal.marketnote.community.domain.like.Like;

import java.util.Optional;

public class LikeJpaEntityToDomainMapper {
    public static Optional<Like> mapToDomain(LikeJpaEntity entity) {
        if (!FormatValidator.hasValue(entity)) {
            return Optional.empty();
        }

        return Optional.of(
                Like.of(
                        entity.getId().getTargetType(),
                        entity.getId().getTargetId(),
                        entity.getId().getUserId(),
                        entity.getStatus(),
                        entity.getCreatedAt(),
                        entity.getModifiedAt()
                )
        );
    }
}
