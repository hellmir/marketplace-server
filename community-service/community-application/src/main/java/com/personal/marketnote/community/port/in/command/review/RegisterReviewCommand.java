package com.personal.marketnote.community.port.in.command.review;

public record RegisterReviewCommand(
        Long reviewerId,
        Long orderId,
        Long productId,
        Long pricePolicyId,
        String selectedOptions,
        Integer quantity,
        String reviewerName,
        Float rating,
        String content,
        Boolean isPhoto
) {
    public static RegisterReviewCommand of(
            Long reviewerId,
            Long orderId,
            Long productId,
            Long pricePolicyId,
            String selectedOptions,
            Integer quantity,
            String reviewerName,
            Float rating,
            String content,
            Boolean isPhoto
    ) {
        return new RegisterReviewCommand(
                reviewerId, orderId, productId, pricePolicyId, selectedOptions,
                quantity, reviewerName, rating, content, isPhoto
        );
    }
}
