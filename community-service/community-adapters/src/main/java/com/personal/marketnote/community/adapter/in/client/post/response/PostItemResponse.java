package com.personal.marketnote.community.adapter.in.client.post.response;

import com.personal.marketnote.community.domain.post.PostTargetType;
import com.personal.marketnote.community.port.in.result.post.PostItemResult;

import java.time.LocalDateTime;

public record PostItemResponse(
        Long id,
        Long userId,
        Long parentId,
        String board,
        String category,
        PostTargetType targetType,
        Long targetId,
        String writerName,
        String title,
        String content,
        Boolean isPrivate,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        PostProductInfoResponse product
) {
    public static PostItemResponse from(PostItemResult result) {
        return new PostItemResponse(
                result.id(),
                result.userId(),
                result.parentId(),
                result.board(),
                result.category(),
                result.targetType(),
                result.targetId(),
                result.writerName(),
                result.title(),
                result.content(),
                result.isPrivate(),
                result.createdAt(),
                result.modifiedAt(),
                PostProductInfoResponse.from(result.product())
        );
    }
}
