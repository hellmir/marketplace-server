package com.personal.marketnote.community.port.in.result.review;

import com.personal.marketnote.community.domain.review.ReviewReport;

import java.util.List;

public record GetReviewReportsResult(
        List<ReviewReportItemResult> reviewReports
) {
    public static GetReviewReportsResult from(List<ReviewReport> reviewReports) {
        return new GetReviewReportsResult(
                reviewReports.stream()
                        .map(ReviewReportItemResult::from)
                        .toList()
        );
    }
}
