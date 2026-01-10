package com.personal.marketnote.community.adapter.out.persistence.review;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.community.adapter.out.mapper.ReviewJpaEntityToDomainMapper;
import com.personal.marketnote.community.adapter.out.persistence.review.entity.ReviewJpaEntity;
import com.personal.marketnote.community.adapter.out.persistence.review.repository.ReviewJpaRepository;
import com.personal.marketnote.community.domain.review.Review;
import com.personal.marketnote.community.domain.review.ReviewSortProperty;
import com.personal.marketnote.community.port.out.review.FindReviewPort;
import com.personal.marketnote.community.port.out.review.SaveReviewPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class ReviewPersistenceAdapter implements SaveReviewPort, FindReviewPort {
    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public Review save(Review review) {
        ReviewJpaEntity savedEntity = reviewJpaRepository.save(ReviewJpaEntity.from(review));
        savedEntity.setIdToOrderNum();

        return ReviewJpaEntityToDomainMapper.mapToDomain(savedEntity).orElse(null);
    }

    @Override
    public boolean existsByOrderIdAndPricePolicyId(Long orderId, Long pricePolicyId) {
        return reviewJpaRepository.existsByOrderIdAndPricePolicyId(orderId, pricePolicyId);
    }

    @Override
    public List<Review> findProductReviews(
            Long productId, Boolean isPhoto, Long cursor, Pageable pageable, ReviewSortProperty sortProperty
    ) {
        if (isPhoto) {
            List<ReviewJpaEntity> entities = reviewJpaRepository.findProductPhotoReviewsByCursor(
                    productId,
                    cursor,
                    pageable,
                    sortProperty.getCamelCaseValue()
            );

            return entities.stream()
                    .map(entity -> ReviewJpaEntityToDomainMapper.mapToDomain(entity).orElse(null))
                    .toList();
        }

        List<ReviewJpaEntity> entities = reviewJpaRepository.findProductReviewsByCursor(
                productId,
                cursor,
                pageable,
                sortProperty.getCamelCaseValue()
        );

        return entities.stream()
                .map(entity -> ReviewJpaEntityToDomainMapper.mapToDomain(entity).orElse(null))
                .toList();
    }

    @Override
    public long countActive(Long productId, Boolean isPhoto) {
        if (isPhoto) {
            return reviewJpaRepository.countByProductIdAndPhotoYn(productId, true);
        }

        return reviewJpaRepository.countByProductId(productId);
    }
}
