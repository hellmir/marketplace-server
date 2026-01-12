package com.personal.marketnote.community.port.in.command.review;

public record UpdateReviewCommand(
        Long id,
        Long reviewerId,
        Float rating,
        String content,
        Boolean isPhoto
) {
    public static UpdateReviewCommand of(
            Long id,
            Long reviewerId,
            Float rating,
            String content,
            Boolean isPhoto
    ) {
        return new UpdateReviewCommand(
                id, reviewerId, rating, content, isPhoto
        );
    }
}
