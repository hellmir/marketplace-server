package com.personal.marketnote.community.port.in.usecase.review;

import com.personal.marketnote.community.port.in.command.review.RegisterReviewCommand;

public interface GetReviewUseCase {
    void validateDuplicateReview(RegisterReviewCommand command);
}
