package com.personal.marketnote.community.port.in.result.review;

import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.community.domain.review.ProductReviewAggregate;

public record ProductReviewAggregateSummaryResult(
        Long productId,
        int totalCount,
        float averageRating
) {
    public static ProductReviewAggregateSummaryResult from(
            Long productId,
            ProductReviewAggregate aggregate
    ) {
        if (FormatValidator.hasNoValue(aggregate)) {
            return new ProductReviewAggregateSummaryResult(productId, 0, 0f);
        }

        return new ProductReviewAggregateSummaryResult(
                productId,
                aggregate.getTotalCount(),
                aggregate.getAverageRating()
        );
    }
}
