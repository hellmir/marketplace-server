package com.personal.marketnote.product.adapter.out.response;

import java.util.List;

public record GetProductReviewAggregatesResponse(
        List<ProductReviewAggregateItemResponse> reviewAggregates
) {
}
