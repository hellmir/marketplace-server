package com.personal.marketnote.product.port.out.review;

import com.personal.marketnote.product.port.out.result.ProductReviewAggregateResult;

import java.util.List;
import java.util.Map;

public interface FindProductReviewAggregatesPort {
    Map<Long, ProductReviewAggregateResult> findByProductIds(List<Long> productIds);
}
