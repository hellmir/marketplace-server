package com.personal.marketnote.community.port.in.result.review;

import com.personal.marketnote.community.domain.review.Review;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
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
        Integer likeCount,
        Boolean isUserLiked,
        String status,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        Long orderNum
) {
    public static ReviewItemResult from(Review review) {
        return ReviewItemResult.builder()
                .id(review.getId())
                .reviewerId(review.getReviewerId())
                .orderId(review.getOrderId())
                .productId(review.getProductId())
                .pricePolicyId(review.getPricePolicyId())
                .selectedOptions(review.getSelectedOptions())
                .quantity(review.getQuantity())
                .reviewerName(review.getReviewerName())
                .rating(review.getRating())
                .content(review.getContent())
                .photoYn(review.getPhotoYn())
                .editedYn(review.getEditedYn())
                .likeCount(review.getLikeUserIds().size())
                .isUserLiked(review.getIsUserLiked())
                .status(review.getStatus().name())
                .createdAt(review.getCreatedAt())
                .modifiedAt(review.getModifiedAt())
                .orderNum(review.getOrderNum())
                .build();
    }
}
