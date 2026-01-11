package com.personal.marketnote.community.port.out.review;

import com.personal.marketnote.community.domain.review.ProductReviewAggregate;
import com.personal.marketnote.community.domain.review.Review;
import com.personal.marketnote.community.domain.review.ReviewSortProperty;
import com.personal.marketnote.community.domain.review.Reviews;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FindReviewPort {
    /**
     * @param orderId       주문 ID
     * @param pricePolicyId 가격 정책 ID
     * @return 주문 ID와 가격 정책 ID에 해당하는 리뷰 존재 여부 {@link boolean}
     * @Date 2026-01-10
     * @Author 성효빈
     * @Description 주문 ID와 가격 정책 ID에 해당하는 리뷰 존재 여부를 조회합니다.
     */
    boolean existsByOrderIdAndPricePolicyId(Long orderId, Long pricePolicyId);

    /**
     * @param productId    상품 ID
     * @param isPhoto      포토 리뷰 여부
     * @param cursor       커서(무한 스크롤 페이지 설정)
     * @param pageable     페이지네이션 정보
     * @param sortProperty 정렬 속성
     * @return 상품 리뷰 목록 {@link List<Review>}
     * @Date 2026-01-10
     * @Author 성효빈
     * @Description 상품 리뷰 목록을 조회합니다.
     */
    Reviews findProductReviews(
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

    /**
     * @param productId 상품 ID
     * @return 상품 리뷰 집계 정보 {@link Optional<ProductReviewAggregate>}
     * @Date 2026-01-10
     * @Author 성효빈
     * @Description 상품 리뷰 집계 정보를 조회합니다.
     */
    Optional<ProductReviewAggregate> findProductReviewAggregateByProductId(Long productId);
}
