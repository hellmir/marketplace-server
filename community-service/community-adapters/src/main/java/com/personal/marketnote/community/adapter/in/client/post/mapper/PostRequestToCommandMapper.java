package com.personal.marketnote.community.adapter.in.client.post.mapper;

import com.personal.marketnote.community.adapter.in.client.post.request.RegisterPostRequest;
import com.personal.marketnote.community.port.in.command.post.RegisterPostCommand;

public class PostRequestToCommandMapper {
    public static RegisterPostCommand mapToCommand(RegisterPostRequest request, Long userId) {
        return RegisterPostCommand.builder()
                .userId(userId)
                .parentId(request.getParentId())
                .board(request.getBoard())
                .category(request.getCategory())
                .targetType(request.getTargetType())
                .targetId(request.getTargetId())
                .writerName(request.getWriterName())
                .title(request.getTitle())
                .content(request.getContent())
                .isPrivate(request.getIsPrivate())
                .build();
    }
}
