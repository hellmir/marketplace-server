package com.personal.marketnote.community.port.in.command.post;

import com.personal.marketnote.community.domain.post.Board;
import com.personal.marketnote.community.domain.post.PostSortProperty;
import com.personal.marketnote.community.domain.post.PostTargetType;
import lombok.Builder;
import org.springframework.data.domain.Sort;

@Builder
public record GetPostsCommand(
        Long userId,
        Board board,
        String category,
        PostTargetType targetType,
        Long targetId,
        Long cursor,
        int pageSize,
        Sort.Direction sortDirection,
        PostSortProperty sortProperty
) {
}
