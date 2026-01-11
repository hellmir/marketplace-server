package com.personal.marketnote.community.adapter.in.client.review.response;

import com.personal.marketnote.community.port.in.result.review.ReviewItemResult;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record ReviewItemResponse(
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
        boolean isUserLiked,
        String status,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        Long orderNum
) {
    public static ReviewItemResponse from(ReviewItemResult result) {
        return ReviewItemResponse.builder()
                .id(result.id())
                .reviewerId(result.reviewerId())
                .orderId(result.orderId())
                .productId(result.productId())
                .pricePolicyId(result.pricePolicyId())
                .selectedOptions(result.selectedOptions())
                .quantity(result.quantity())
                .reviewerName(result.reviewerName())
                .rating(result.rating())
                .content(result.content())
                .photoYn(result.photoYn())
                .editedYn(result.editedYn())
                .likeCount(result.likeCount())
                .isUserLiked(result.isUserLiked())
                .status(result.status())
                .createdAt(result.createdAt())
                .modifiedAt(result.modifiedAt())
                .orderNum(result.orderNum())
                .build();
    }
}
