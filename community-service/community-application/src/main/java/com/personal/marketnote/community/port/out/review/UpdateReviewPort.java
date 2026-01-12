package com.personal.marketnote.community.port.out.review;

import com.personal.marketnote.community.domain.review.ProductReviewAggregate;
import com.personal.marketnote.community.domain.review.Review;

public interface UpdateReviewPort {
    void update(Review review);

    void update(ProductReviewAggregate productReviewAggregate);
}
