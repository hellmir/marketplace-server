package com.personal.marketnote.community.port.out.review;

import com.personal.marketnote.community.domain.review.Review;

public interface SaveReviewPort {
    Review save(Review review);
}
