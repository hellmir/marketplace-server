package com.personal.marketnote.community.adapter.in.client.post.response;

import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.community.domain.post.PostTargetType;
import com.personal.marketnote.community.port.in.result.post.PostItemResult;

import java.time.LocalDateTime;
import java.util.List;

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
        boolean isPrivate,
        boolean isMasked,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        PostProductInfoResponse product,
        List<PostItemResponse> replies
) {
    public static PostItemResponse from(PostItemResult result) {
        List<PostItemResult> replies = result.getReplies();

        return new PostItemResponse(
                result.getId(),
                result.getUserId(),
                result.getParentId(),
                result.getBoard(),
                result.getCategory(),
                result.getTargetType(),
                result.getTargetId(),
                result.getWriterName(),
                result.getTitle(),
                result.getContent(),
                result.isPrivate(),
                result.isMasked(),
                result.getCreatedAt(),
                result.getModifiedAt(),
                PostProductInfoResponse.from(result.getProduct()),
                FormatValidator.hasValue(replies)
                        ? replies.stream()
                        .map(PostItemResponse::from)
                        .toList()
                        : List.of()
        );
    }
}
