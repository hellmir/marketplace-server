package com.personal.marketnote.community.adapter.in.client.review.response;

import com.personal.marketnote.community.port.in.result.review.GetReviewResult;

public record GetReviewResponse(
        Long id,
        Long orderId,
        Long pricePolicyId,
        Long userId,
        Float score,
        String content
) {
    public static GetReviewResponse from(GetReviewResult result) {
        return new GetReviewResponse(
                result.id(),
                result.orderId(),
                result.pricePolicyId(),
                result.userId(),
                result.score(),
                result.content()
        );
    }
}
