package com.personal.marketnote.community.mapper;

import com.personal.marketnote.community.domain.review.ReviewCreateState;
import com.personal.marketnote.community.port.in.command.review.RegisterReviewCommand;

public class ReviewCommandToStateMapper {
    public static ReviewCreateState mapToState(RegisterReviewCommand command) {
        return ReviewCreateState.builder()
                .reviewerId(command.reviewerId())
                .orderId(command.orderId())
                .productId(command.productId())
                .pricePolicyId(command.pricePolicyId())
                .selectedOptions(command.selectedOptions())
                .quantity(command.quantity())
                .reviewerName(command.reviewerName())
                .rating(command.rating())
                .content(command.content())
                .isPhoto(command.isPhoto())
                .build();
    }
}
