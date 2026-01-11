package com.personal.marketnote.community.port.in.result.review;

import com.personal.marketnote.community.domain.review.ProductReviewAggregate;

public record ProductReviewAggregateResult(
        int totalCount,
        int fivePointCount,
        int fourPointCount,
        int threePointCount,
        int twoPointCount,
        int onePointCount,
        float averageRating
) {
    public static ProductReviewAggregateResult from(
            ProductReviewAggregate productReviewAggregate
    ) {
        return new ProductReviewAggregateResult(
                productReviewAggregate.getTotalCount(),
                productReviewAggregate.getFivePointCount(),
                productReviewAggregate.getFourPointCount(),
                productReviewAggregate.getThreePointCount(),
                productReviewAggregate.getTwoPointCount(),
                productReviewAggregate.getOnePointCount(),
                productReviewAggregate.getAverageRating()
        );
    }
}
