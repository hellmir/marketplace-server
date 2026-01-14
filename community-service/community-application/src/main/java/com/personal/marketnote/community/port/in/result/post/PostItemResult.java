package com.personal.marketnote.community.port.in.result.post;

import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.community.domain.post.Post;
import com.personal.marketnote.community.domain.post.PostTargetType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class PostItemResult {
    private Long id;
    private Long userId;
    private Long parentId;
    private String board;
    private String category;
    private PostTargetType targetType;
    private Long targetId;
    private String writerName;
    private String title;
    private String content;
    private boolean isPrivate;
    private boolean isMasked;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private PostProductInfoResult product;
    private List<PostItemResult> replies;

    public static PostItemResult from(Post post, PostProductInfoResult productInfo) {
        String categoryCode = null;
        if (FormatValidator.hasValue(post.getCategory())) {
            categoryCode = post.getCategory().getCode();
        }

        return PostItemResult.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .parentId(post.getParentId())
                .board(post.getBoard().name())
                .category(categoryCode)
                .targetType(post.getTargetType())
                .targetId(post.getTargetId())
                .writerName(post.getWriterName())
                .title(post.getTitle())
                .content(post.getContent())
                .isPrivate(post.getIsPrivate())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .product(productInfo)
                .build();
    }

    public static PostItemResult from(Post post) {
        String categoryCode = null;
        if (FormatValidator.hasValue(post.getCategory())) {
            categoryCode = post.getCategory().getCode();
        }

        return PostItemResult.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .parentId(post.getParentId())
                .board(post.getBoard().name())
                .category(categoryCode)
                .targetType(post.getTargetType())
                .targetId(post.getTargetId())
                .writerName(post.getWriterName())
                .title(post.getTitle())
                .content(post.getContent())
                .isPrivate(post.getIsPrivate())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }

    public void maskPrivatePost(Long userId) {
        if (isPrivate && !FormatValidator.equals(userId, this.userId)) {
            maskContent();
            isMasked = true;
        }
    }

    private void maskContent() {
        title = null;
        content = null;
    }

    public void addReplies(Post post) {
        replies = post.getReplies().stream().map(PostItemResult::from).toList();
    }
}
