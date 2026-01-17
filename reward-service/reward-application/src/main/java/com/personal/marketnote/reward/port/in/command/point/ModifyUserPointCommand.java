package com.personal.marketnote.reward.port.in.command.point;

import com.personal.marketnote.reward.domain.point.UserPointChangeType;
import com.personal.marketnote.reward.domain.point.UserPointHistorySourceType;

public record ModifyUserPointCommand(
        Long userId,
        UserPointChangeType changeType,
        Long amount,
        UserPointHistorySourceType sourceType,
        Long sourceId,
        String reason
) {
    public static ModifyUserPointCommand of(
            Long userId,
            UserPointChangeType changeType,
            Long amount,
            UserPointHistorySourceType sourceType,
            Long sourceId,
            String reason
    ) {
        return new ModifyUserPointCommand(userId, changeType, amount, sourceType, sourceId, reason);
    }
}
