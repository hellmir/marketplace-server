package com.personal.marketnote.community.adapter.in.web.review.response;

import com.personal.marketnote.community.port.in.result.review.ProductReviewAggregateSummaryResult;

public record ProductReviewAggregateSummaryResponse(
        Long productId,
        int totalCount,
        float averageRating
) {
    public static ProductReviewAggregateSummaryResponse from(
            ProductReviewAggregateSummaryResult result
    ) {
        return new ProductReviewAggregateSummaryResponse(
                result.productId(),
                result.totalCount(),
                result.averageRating()
        );
    }
}
