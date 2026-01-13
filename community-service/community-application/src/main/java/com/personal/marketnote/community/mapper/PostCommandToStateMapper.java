package com.personal.marketnote.community.mapper;

import com.personal.marketnote.community.domain.post.PostCreateState;
import com.personal.marketnote.community.port.in.command.post.RegisterPostCommand;

public class PostCommandToStateMapper {
    public static PostCreateState mapToState(RegisterPostCommand command) {
        return PostCreateState.builder()
                .userId(command.userId())
                .parentId(command.parentId())
                .board(command.board())
                .category(command.category())
                .targetType(command.targetType())
                .targetId(command.targetId())
                .writerName(command.writerName())
                .title(command.title())
                .content(command.content())
                .isPrivate(command.isPrivate())
                .build();
    }
}
