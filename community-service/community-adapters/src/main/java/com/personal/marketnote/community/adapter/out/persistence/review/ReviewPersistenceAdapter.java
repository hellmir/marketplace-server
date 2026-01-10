package com.personal.marketnote.community.adapter.out.persistence.review;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.community.adapter.out.mapper.ProductReviewAggregateJpaEntityToDomainMapper;
import com.personal.marketnote.community.adapter.out.mapper.ReviewJpaEntityToDomainMapper;
import com.personal.marketnote.community.adapter.out.persistence.review.entity.ProductReviewAggregateJpaEntity;
import com.personal.marketnote.community.adapter.out.persistence.review.entity.ReviewJpaEntity;
import com.personal.marketnote.community.adapter.out.persistence.review.repository.ProductReviewAggregateJpaRepository;
import com.personal.marketnote.community.adapter.out.persistence.review.repository.ReviewJpaRepository;
import com.personal.marketnote.community.domain.review.ProductReviewAggregate;
import com.personal.marketnote.community.domain.review.Review;
import com.personal.marketnote.community.domain.review.ReviewSortProperty;
import com.personal.marketnote.community.exception.ProductReviewAggregateNotFoundException;
import com.personal.marketnote.community.port.out.review.FindReviewPort;
import com.personal.marketnote.community.port.out.review.SaveReviewPort;
import com.personal.marketnote.community.port.out.review.UpdateReviewPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class ReviewPersistenceAdapter implements SaveReviewPort, FindReviewPort, UpdateReviewPort {
    private final ReviewJpaRepository reviewJpaRepository;
    private final ProductReviewAggregateJpaRepository productReviewAggregateJpaRepository;

    @Override
    public Review save(Review review) {
        ReviewJpaEntity savedEntity = reviewJpaRepository.save(ReviewJpaEntity.from(review));
        savedEntity.setIdToOrderNum();

        return ReviewJpaEntityToDomainMapper.mapToDomain(savedEntity).orElse(null);
    }

    @Override
    public void save(ProductReviewAggregate productReviewAggregate) {
        productReviewAggregateJpaRepository.save(ProductReviewAggregateJpaEntity.from(productReviewAggregate));
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

    @Override
    public Optional<ProductReviewAggregate> findProductReviewAggregateByProductId(Long productId) {
        return ProductReviewAggregateJpaEntityToDomainMapper.mapToDomain(
                productReviewAggregateJpaRepository.findByProductId(productId).orElse(null)
        );
    }

    @Override
    public void update(ProductReviewAggregate productReviewAggregate) throws ProductReviewAggregateNotFoundException {
        ProductReviewAggregateJpaEntity entity = findEntityByProductId(productReviewAggregate.getProductId());
        entity.updateFrom(productReviewAggregate);
    }

    private ProductReviewAggregateJpaEntity findEntityByProductId(Long productId)
            throws ProductReviewAggregateNotFoundException {
        return productReviewAggregateJpaRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductReviewAggregateNotFoundException(productId));
    }
}
