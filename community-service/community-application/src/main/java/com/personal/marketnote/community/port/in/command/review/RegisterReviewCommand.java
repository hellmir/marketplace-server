package com.personal.marketnote.community.port.in.command.review;

public record RegisterReviewCommand(
        Long orderId,
        Long pricePolicyId,
        Long userId,
        Float score,
        String content
) {
    public static RegisterReviewCommand of(
            Long orderId,
            Long pricePolicyId,
            Long userId,
            Float score,
            String content
    ) {
        return new RegisterReviewCommand(orderId, pricePolicyId, userId, score, content);
    }
}
