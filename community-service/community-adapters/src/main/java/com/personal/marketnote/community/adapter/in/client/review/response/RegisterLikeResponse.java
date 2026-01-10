package com.personal.marketnote.community.adapter.in.client.review.response;

import com.personal.marketnote.community.port.in.result.like.RegisterLikeResult;

public record RegisterLikeResponse(
        String targetType,
        Long targetId,
        Long userId
) {
    public static RegisterLikeResponse from(RegisterLikeResult result) {
        return new RegisterLikeResponse(
                result.targetType().name(),
                result.targetId(),
                result.userId()
        );
    }
}
