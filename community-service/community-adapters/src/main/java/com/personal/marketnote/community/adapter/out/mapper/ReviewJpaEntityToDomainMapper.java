package com.personal.marketnote.community.adapter.out.mapper;

import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.community.adapter.out.persistence.review.entity.ReviewJpaEntity;
import com.personal.marketnote.community.domain.review.Review;

import java.util.Optional;

public class ReviewJpaEntityToDomainMapper {
    public static Optional<Review> mapToDomain(ReviewJpaEntity entity) {
        if (!FormatValidator.hasValue(entity)) {
            return Optional.empty();
        }

        return Optional.of(
                Review.of(
                        entity.getId(),
                        entity.getOrderId(),
                        entity.getPricePolicyId(),
                        entity.getUserId(),
                        entity.getScore(),
                        entity.getContent(),
                        entity.getStatus(),
                        entity.getCreatedAt(),
                        entity.getModifiedAt()
                )
        );
    }
}
