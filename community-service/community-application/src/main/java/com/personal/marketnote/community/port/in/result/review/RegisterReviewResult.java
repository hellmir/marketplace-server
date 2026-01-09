package com.personal.marketnote.community.port.in.result.review;

import com.personal.marketnote.community.domain.review.Review;

public record RegisterReviewResult(
        Long id
) {
    public static RegisterReviewResult from(Review review) {
        return new RegisterReviewResult(review.getId());
    }
}
