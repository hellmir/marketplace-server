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
                        entity.getReviewerId(),
                        entity.getOrderId(),
                        entity.getProductId(),
                        entity.getPricePolicyId(),
                        entity.getSelectedOptions(),
                        entity.getQuantity(),
                        entity.getReviewerName(),
                        entity.getRating(),
                        entity.getContent(),
                        entity.getPhotoYn(),
                        entity.getEditedYn(),
                        entity.getStatus(),
                        entity.getCreatedAt(),
                        entity.getModifiedAt(),
                        entity.getOrderNum()
                )
        );
    }
}
