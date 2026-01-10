package com.personal.marketnote.community.port.out.review;

import com.personal.marketnote.community.domain.review.Review;
import com.personal.marketnote.community.domain.review.ReviewSortProperty;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FindReviewPort {
    boolean existsByOrderIdAndPricePolicyId(Long orderId, Long pricePolicyId);

    List<Review> findProductReviews(
            Long productId, Boolean isPhoto, Long cursor, Pageable pageable, ReviewSortProperty sortProperty
    );

    /**
     * @param productId 상품 ID
     * @param isPhoto   포토 리뷰 여부
     * @return 총 리뷰 개수 {@link long}
     * @Date 2026-01-10
     * @Author 성효빈
     * @Description 총 리뷰 개수를 조회합니다.
     */
    long countActive(Long productId, Boolean isPhoto);
}
