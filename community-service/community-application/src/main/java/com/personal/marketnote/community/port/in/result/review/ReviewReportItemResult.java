package com.personal.marketnote.community.port.in.result.review;

import com.personal.marketnote.community.domain.review.ReviewReport;

import java.time.LocalDateTime;

public record ReviewReportItemResult(
        Long reviewId,
        Long reporterId,
        String reason,
        LocalDateTime createdAt
) {
    public static ReviewReportItemResult from(ReviewReport reviewReport) {
        return new ReviewReportItemResult(
                reviewReport.getReviewId(),
                reviewReport.getReporterId(),
                reviewReport.getReason(),
                reviewReport.getCreatedAt()
        );
    }
}
