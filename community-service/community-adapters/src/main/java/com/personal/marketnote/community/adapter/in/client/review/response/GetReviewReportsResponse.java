package com.personal.marketnote.community.adapter.in.client.review.response;

import com.personal.marketnote.community.port.in.result.review.GetReviewReportsResult;
import com.personal.marketnote.community.port.in.result.review.ReviewReportItemResult;

import java.util.List;

public record GetReviewReportsResponse(
        List<ReviewReportItemResult> reviewReports
) {
    public static GetReviewReportsResponse from(GetReviewReportsResult result) {
        return new GetReviewReportsResponse(result.reviewReports());
    }
}
