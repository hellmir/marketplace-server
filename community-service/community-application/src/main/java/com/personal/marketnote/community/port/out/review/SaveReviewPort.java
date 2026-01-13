package com.personal.marketnote.community.port.out.review;

import com.personal.marketnote.community.domain.review.ProductReviewAggregate;
import com.personal.marketnote.community.domain.review.Review;
import com.personal.marketnote.community.domain.review.ReviewVersionHistory;

public interface SaveReviewPort {
    Review saveAggregate(Review review);

    void saveAggregate(ProductReviewAggregate productReviewAggregate);

    void saveVersionHistory(ReviewVersionHistory reviewVersionHistory);
}
