package com.personal.marketnote.community.adapter.in.client.review.mapper;

import com.personal.marketnote.community.adapter.in.client.review.request.RegisterReviewRequest;
import com.personal.marketnote.community.adapter.in.client.review.request.ReportReviewRequest;
import com.personal.marketnote.community.adapter.in.client.review.request.UpdateReviewRequest;
import com.personal.marketnote.community.port.in.command.review.RegisterReviewCommand;
import com.personal.marketnote.community.port.in.command.review.ReportReviewCommand;
import com.personal.marketnote.community.port.in.command.review.UpdateReviewCommand;

public class ReviewRequestToCommandMapper {
    public static RegisterReviewCommand mapToCommand(RegisterReviewRequest request, Long userId) {
        return RegisterReviewCommand.builder()
                .reviewerId(userId)
                .orderId(request.getOrderId())
                .productId(request.getProductId())
                .pricePolicyId(request.getPricePolicyId())
                .selectedOptions(request.getSelectedOptions())
                .quantity(request.getQuantity())
                .reviewerName(request.getReviewerName())
                .rating(request.getRating())
                .content(request.getContent())
                .isPhoto(request.getIsPhoto())
                .build();
    }

    public static UpdateReviewCommand mapToCommand(Long id, UpdateReviewRequest request, Long userId) {
        return UpdateReviewCommand.builder()
                .id(id)
                .reviewerId(userId)
                .rating(request.getRating())
                .content(request.getContent())
                .isPhoto(request.getIsPhoto())
                .build();
    }

    public static ReportReviewCommand mapToCommand(Long id, Long userId, ReportReviewRequest request) {
        return ReportReviewCommand.of(id, userId, request.getReason());
    }
}
