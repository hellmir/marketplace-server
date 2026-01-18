package com.personal.marketnote.community.port.in.result.review;

import com.personal.marketnote.common.application.file.port.in.result.GetFileResult;
import com.personal.marketnote.community.domain.review.Review;

import java.util.List;
import java.util.Map;

public record GetReviewsResult(
        Long totalElements,
        Long nextCursor,
        boolean hasNext,
        List<ReviewItemResult> reviews
) {
    public static GetReviewsResult from(
            boolean hasNext,
            Long nextCursor,
            Long totalElements,
            List<Review> reviews,
            Map<Long, List<GetFileResult>> reviewImagesByReviewId
    ) {
        return new GetReviewsResult(
                totalElements,
                nextCursor,
                hasNext,
                reviews.stream()
                        .map(review -> ReviewItemResult.from(review, reviewImagesByReviewId.get(review.getId())))
                        .toList()
        );
    }
}
