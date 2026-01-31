package com.personal.marketnote.product.adapter.out.response;

public record ProductReviewAggregateItemResponse(
        Long productId,
        int totalCount,
        float averageRating
) {
}
