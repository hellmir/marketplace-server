package com.personal.marketnote.community.adapter.in.client.review.response;

import com.personal.marketnote.community.port.in.result.review.ReviewItemResult;

import java.time.LocalDateTime;

public record ReviewItemResponse(
        Long id,
        Long reviewerId,
        Long orderId,
        Long pricePolicyId,
        String selectedOptions,
        Integer quantity,
        String reviewerName,
        Float rating,
        String content,
        Boolean photoYn,
        Boolean editedYn,
        String status,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        Long orderNum
) {
    public static ReviewItemResponse from(ReviewItemResult result) {
        return new ReviewItemResponse(
                result.id(),
                result.reviewerId(),
                result.orderId(),
                result.pricePolicyId(),
                result.selectedOptions(),
                result.quantity(),
                result.reviewerName(),
                result.rating(),
                result.content(),
                result.photoYn(),
                result.editedYn(),
                result.status(),
                result.createdAt(),
                result.modifiedAt(),
                result.orderNum()
        );
    }
}
