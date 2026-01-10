package com.personal.marketnote.community.port.in.result.review;

import com.personal.marketnote.community.domain.review.Review;

import java.time.LocalDateTime;

public record ReviewItemResult(
        Long id,
        Long reviewerId,
        Long orderId,
        Long productId,
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
    public static ReviewItemResult from(Review review) {
        return new ReviewItemResult(
                review.getId(),
                review.getReviewerId(),
                review.getOrderId(),
                review.getProductId(),
                review.getPricePolicyId(),
                review.getSelectedOptions(),
                review.getQuantity(),
                review.getReviewerName(),
                review.getRating(),
                review.getContent(),
                review.getPhotoYn(),
                review.getEditedYn(),
                review.getStatus().name(),
                review.getCreatedAt(),
                review.getModifiedAt(),
                review.getOrderNum()
        );
    }
}
