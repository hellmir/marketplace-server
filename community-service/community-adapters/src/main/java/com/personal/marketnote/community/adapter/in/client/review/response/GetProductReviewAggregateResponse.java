package com.personal.marketnote.community.adapter.in.client.review.response;

import com.personal.marketnote.community.port.in.result.review.ProductReviewAggregateResult;

public record GetProductReviewAggregateResponse(
        int totalCount,
        int fivePointCount,
        int fourPointCount,
        int threePointCount,
        int twoPointCount,
        int onePointCount,
        float averageRating
) {
    public static GetProductReviewAggregateResponse from(ProductReviewAggregateResult result) {
        return new GetProductReviewAggregateResponse(
                result.totalCount(),
                result.fivePointCount(),
                result.fourPointCount(),
                result.threePointCount(),
                result.twoPointCount(),
                result.onePointCount(),
                result.averageRating()
        );
    }
}
