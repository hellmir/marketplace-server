package com.personal.marketnote.community.port.in.usecase.review;

import com.personal.marketnote.community.port.in.command.review.RegisterReviewCommand;
import com.personal.marketnote.community.port.in.result.review.RegisterReviewResult;

public interface RegisterReviewUseCase {
    RegisterReviewResult registerReview(RegisterReviewCommand command);
}
