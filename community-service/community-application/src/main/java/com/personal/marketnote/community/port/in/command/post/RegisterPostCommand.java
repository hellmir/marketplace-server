package com.personal.marketnote.community.port.in.command.post;

import com.personal.marketnote.community.domain.post.Board;
import com.personal.marketnote.community.domain.post.PostTargetType;
import lombok.Builder;

@Builder
public record RegisterPostCommand(
        Long userId,
        Long parentId,
        Board board,
        String category,
        PostTargetType targetType,
        Long targetId,
        String writerName,
        String title,
        String content,
        Boolean isPrivate
) {
}
