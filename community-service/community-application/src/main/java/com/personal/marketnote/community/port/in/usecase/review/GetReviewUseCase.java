package com.personal.marketnote.community.port.in.usecase.review;

import com.personal.marketnote.community.domain.review.ReviewSortProperty;
import com.personal.marketnote.community.port.in.command.review.RegisterReviewCommand;
import com.personal.marketnote.community.port.in.result.review.GetReviewsResult;
import org.springframework.data.domain.Sort;

public interface GetReviewUseCase {
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
}
