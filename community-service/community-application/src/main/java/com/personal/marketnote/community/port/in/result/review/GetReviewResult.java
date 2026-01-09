package com.personal.marketnote.community.port.in.result.review;

import com.personal.marketnote.community.domain.review.Review;

public record GetReviewResult(
        Long id,
        Long orderId,
        Long pricePolicyId,
        Long userId,
        Float score,
        String content
) {
    public static GetReviewResult from(Review review) {
        return new GetReviewResult(
                review.getId(),
                review.getOrderId(),
                review.getPricePolicyId(),
                review.getUserId(),
                review.getScore(),
                review.getContent()
        );
    }
}
