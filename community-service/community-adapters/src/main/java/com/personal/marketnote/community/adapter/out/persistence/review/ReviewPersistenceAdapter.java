package com.personal.marketnote.community.adapter.out.persistence.review;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.community.adapter.out.mapper.ReviewJpaEntityToDomainMapper;
import com.personal.marketnote.community.adapter.out.persistence.review.entity.ReviewJpaEntity;
import com.personal.marketnote.community.adapter.out.persistence.review.repository.ReviewJpaRepository;
import com.personal.marketnote.community.domain.review.Review;
import com.personal.marketnote.community.port.out.review.FindReviewPort;
import com.personal.marketnote.community.port.out.review.SaveReviewPort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ReviewPersistenceAdapter implements SaveReviewPort, FindReviewPort {
    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public Review save(Review review) {
        ReviewJpaEntity savedReview = reviewJpaRepository.save(ReviewJpaEntity.from(review));
        return ReviewJpaEntityToDomainMapper.mapToDomain(savedReview).orElse(null);
    }

    @Override
    public boolean existsByOrderIdAndPricePolicyId(Long orderId, Long pricePolicyId) {
        return reviewJpaRepository.existsByOrderIdAndPricePolicyId(orderId, pricePolicyId);
    }
}
