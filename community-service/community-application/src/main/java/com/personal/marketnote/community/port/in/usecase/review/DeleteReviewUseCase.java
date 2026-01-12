package com.personal.marketnote.community.port.in.usecase.review;

public interface DeleteReviewUseCase {
    /**
     * @param id         리뷰 ID
     * @param reviewerId 리뷰 작성자 ID
     * @Date 2026-01-12
     * @Author 성효빈
     * @Description 리뷰를 삭제합니다.
     */
    void deleteReview(Long id, Long reviewerId);
}
