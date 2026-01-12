package com.personal.marketnote.community.port.in.usecase.review;

import com.personal.marketnote.community.domain.review.ProductReviewAggregate;
import com.personal.marketnote.community.domain.review.Review;
import com.personal.marketnote.community.domain.review.ReviewSortProperty;
import com.personal.marketnote.community.port.in.command.review.RegisterReviewCommand;
import com.personal.marketnote.community.port.in.result.review.GetReviewsResult;
import org.springframework.data.domain.Sort;

public interface GetReviewUseCase {
    /**
     * @param id 리뷰 ID
     * @return 리뷰 {@link Review}
     * @Date 2026-01-12
     * @Author 성효빈
     * @Description 리뷰를 조회합니다.
     */
    Review getReview(Long id);

    /**
     * @param command 리뷰 등록 커맨드
     * @Date 2026-01-09
     * @Author 성효빈
     * @Description 리뷰 등록 시 중복 검증을 수행합니다.
     */
    void validateDuplicateReview(RegisterReviewCommand command);

    /**
     * @param userId        회원 ID
     * @param productId     상품 ID
     * @param isPhoto       포토 리뷰 여부
     * @param cursor        커서(무한 스크롤 페이지 설정)
     * @param pageSize      페이지 크기
     * @param sortDirection 정렬 방향
     * @param sortProperty  정렬 속성
     * @return 상품 리뷰 목록 조회 결과 {@link GetReviewsResult}
     * @Date 2026-01-10
     * @Author 성효빈
     * @Description 상품 리뷰 목록을 조회합니다.
     */
    GetReviewsResult getProductReviews(
            Long userId,
            Long productId,
            Boolean isPhoto,
            Long cursor,
            int pageSize,
            Sort.Direction sortDirection,
            ReviewSortProperty sortProperty
    );

    /**
     * @param productId 상품 ID
     * @return 리뷰 개수가 100개 미만인지 여부 {@link boolean}
     * @Date 2026-01-10
     * @Author 성효빈
     * @Description 리뷰 개수가 100개 미만인지 여부를 조회합니다.
     */
    boolean isReviewCountUnderHundred(Long productId);

    /**
     * @param productId 상품 ID
     * @return 상품 리뷰 집계 정보 {@link ProductReviewAggregate}
     * @Date 2026-01-10
     * @Author 성효빈
     * @Description 상품 리뷰 집계 정보를 조회합니다.
     */
    ProductReviewAggregate getProductReviewAggregate(Long productId);

    /**
     * @param id         리뷰 ID
     * @param reviewerId 리뷰 작성자 ID
     * @Date 2026-01-12
     * @Author 성효빈
     * @Description 리뷰 작성자 여부를 검증합니다.
     */
    void validateAuthor(Long id, Long reviewerId);

    /**
     * @param userId        회원 ID
     * @param cursor        커서(무한 스크롤 페이지 설정)
     * @param pageSize      페이지 크기
     * @param sortDirection 정렬 방향
     * @param sortProperty  정렬 속성
     * @return 나의 리뷰 목록 조회 결과 {@link GetReviewsResult}
     * @Date 2026-01-12
     * @Author 성효빈
     * @Description 회원 리뷰 목록을 조회합니다.
     */
    GetReviewsResult getMyReviews(
            Long userId, Long cursor, int pageSize, Sort.Direction sortDirection, ReviewSortProperty sortProperty
    );
}
