package com.personal.marketnote.community.port.in.result.post;

import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.community.domain.post.Post;
import com.personal.marketnote.community.domain.post.PostTargetType;

import java.time.LocalDateTime;
import java.util.List;

public record PostItemResult(
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
        PostProductInfoResult product,
        List<PostItemResult> replies
) {
    public static PostItemResult from(Post post, PostProductInfoResult productInfo) {
        String categoryCode = post.getCategory() == null ? null : post.getCategory().getCode();

        return new PostItemResult(
                post.getId(),
                post.getUserId(),
                post.getParentId(),
                post.getBoard().name(),
                categoryCode,
                post.getTargetType(),
                post.getTargetId(),
                post.getWriterName(),
                post.getTitle(),
                post.getContent(),
                post.getIsPrivate(),
                post.getCreatedAt(),
                post.getModifiedAt(),
                productInfo,
                post.getReplies() == null
                        ? List.of()
                        : post.getReplies().stream()
                        .map(PostItemResult::from)
                        .toList()
        );
    }

    public static PostItemResult from(Post post) {
        String categoryCode = null;
        if (FormatValidator.hasValue(post.getCategory())) {
            categoryCode = post.getCategory().getCode();
        }

        return new PostItemResult(
                post.getId(),
                post.getUserId(),
                post.getParentId(),
                post.getBoard().name(),
                categoryCode,
                post.getTargetType(),
                post.getTargetId(),
                post.getWriterName(),
                post.getTitle(),
                post.getContent(),
                post.getIsPrivate(),
                post.getCreatedAt(),
                post.getModifiedAt(),
                null,
                post.getReplies() == null
                        ? List.of()
                        : post.getReplies().stream()
                        .map(PostItemResult::from)
                        .toList()
        );
    }
}
