package com.personal.marketnote.community.adapter.in.client.review.mapper;

import com.personal.marketnote.community.adapter.in.client.review.request.RegisterReviewRequest;
import com.personal.marketnote.community.adapter.in.client.review.request.UpdateReviewRequest;
import com.personal.marketnote.community.port.in.command.review.RegisterReviewCommand;
import com.personal.marketnote.community.port.in.command.review.UpdateReviewCommand;

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

    public static UpdateReviewCommand mapToCommand(Long id, UpdateReviewRequest request, Long userId) {
        return UpdateReviewCommand.of(
                id,
                userId,
                request.getRating(),
                request.getContent(),
                request.getIsPhoto()
        );
    }
}
