package com.personal.marketnote.community.adapter.in.web.review.response;

import com.personal.marketnote.community.port.in.result.review.RegisterReviewResult;

public record RegisterReviewResponse(
        Long id
) {
    public static RegisterReviewResponse from(RegisterReviewResult result) {
        return new RegisterReviewResponse(result.id());
    }
}
