package com.personal.marketnote.product.port.out.result;

public record ProductReviewAggregateResult(
        Long productId,
        Integer totalCount,
        Float averageRating
) {
    public static ProductReviewAggregateResult empty(Long productId) {
        return new ProductReviewAggregateResult(productId, 0, 0f);
    }
}
