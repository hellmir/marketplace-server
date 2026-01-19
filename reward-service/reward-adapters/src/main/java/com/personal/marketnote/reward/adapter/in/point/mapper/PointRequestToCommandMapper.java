package com.personal.marketnote.reward.adapter.in.point.mapper;

import com.personal.marketnote.reward.adapter.in.point.request.ModifyUserPointRequest;
import com.personal.marketnote.reward.port.in.command.point.ModifyUserPointCommand;

public class PointRequestToCommandMapper {
    public static ModifyUserPointCommand mapToModifyUserPointCommand(
            Long userId,
            ModifyUserPointRequest request
    ) {
        return ModifyUserPointCommand.builder()
                .userId(userId)
                .changeType(request.getChangeType())
                .amount(request.getAmount())
                .sourceType(request.getSourceType())
                .sourceId(request.getSourceId())
                .reason(request.getReason())
                .build();
    }
}

