package com.personal.marketnote.community.port.out.review;

import com.personal.marketnote.community.domain.review.ProductReviewAggregate;

public interface UpdateReviewPort {
    void update(ProductReviewAggregate productReviewAggregate);
}
