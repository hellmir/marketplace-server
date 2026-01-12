package com.personal.marketnote.community.port.in.usecase.review;

import com.personal.marketnote.community.port.in.command.review.ReportReviewCommand;

public interface ReportReviewUseCase {
    /**
     * @param command 리뷰 신고 커맨드
     * @Date 2026-01-12
     * @Author 성효빈
     * @Description 리뷰를 신고합니다.
     */
    void reportReview(ReportReviewCommand command);
}
