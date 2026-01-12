package com.personal.marketnote.community.port.out.review;

import com.personal.marketnote.community.domain.review.ReviewReport;

import java.util.List;

public interface FindReviewReportPort {
    /**
     * @param reviewId   리뷰 ID
     * @param reporterId 신고자 ID
     * @return 리뷰 신고 여부 {@link boolean}
     * @Date 2026-01-12
     * @Author 성효빈
     * @Description 리뷰 신고 여부를 조회합니다.
     */
    boolean existsByReviewIdAndReporterId(Long reviewId, Long reporterId);

    /**
     * @param reviewId 리뷰 ID
     * @return 리뷰 신고 내역 {@link List<ReviewReport>}
     * @Date 2026-01-12
     * @Author 성효빈
     * @Description 리뷰 신고 내역을 조회합니다.
     */
    List<ReviewReport> findByReviewId(Long reviewId);
}
