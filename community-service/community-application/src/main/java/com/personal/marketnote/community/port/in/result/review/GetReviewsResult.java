package com.personal.marketnote.community.port.in.result.review;

import com.personal.marketnote.community.domain.review.Review;

import java.util.List;

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
            List<Review> reviews
    ) {
        return new GetReviewsResult(
                totalElements,
                nextCursor,
                hasNext,
                reviews.stream()
                        .map(ReviewItemResult::from)
                        .toList()
        );
    }
}
