package com.personal.marketnote.community.adapter.in.client.review.mapper;

import com.personal.marketnote.community.adapter.in.client.review.request.RegisterReviewRequest;
import com.personal.marketnote.community.port.in.command.review.RegisterReviewCommand;

public class ReviewRequestToCommandMapper {
    public static RegisterReviewCommand mapToCommand(RegisterReviewRequest request, Long userId) {
        return RegisterReviewCommand.of(
                userId,
                request.getOrderId(),
                request.getProductId(),
                request.getPricePolicyId(),
                request.getSelectedOptions(),
                request.getQuantity(),
                request.getReviewerName(),
                request.getRating(),
                request.getContent(),
                request.getIsPhoto()
        );
    }
}
