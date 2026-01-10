package com.personal.marketnote.community.adapter.in.client.review.mapper;

import com.personal.marketnote.community.adapter.in.client.review.request.RegisterLikeRequest;
import com.personal.marketnote.community.port.in.command.like.RegisterLikeCommand;

public class LikeRequestToCommandMapper {
    public static RegisterLikeCommand mapToCommand(RegisterLikeRequest request, Long userId) {
        return RegisterLikeCommand.of(request.targetType(), request.targetId(), userId);
    }
}
