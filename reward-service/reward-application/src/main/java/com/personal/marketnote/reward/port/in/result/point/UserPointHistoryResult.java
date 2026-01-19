package com.personal.marketnote.reward.port.in.result.point;

import com.personal.marketnote.reward.domain.point.UserPointHistory;
import com.personal.marketnote.reward.domain.point.UserPointSourceType;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record UserPointHistoryResult(
        Long id,
        Long amount,
        Boolean isReflected,
        UserPointSourceType sourceType,
        Long sourceId,
        String reason,
        LocalDateTime accumulatedAt,
        LocalDateTime createdAt
) {
    public static UserPointHistoryResult from(UserPointHistory history) {
        return UserPointHistoryResult.builder()
                .id(history.getId())
                .amount(history.getAmount())
                .isReflected(history.getIsReflected())
                .sourceType(history.getSourceType())
                .sourceId(history.getSourceId())
                .reason(history.getReason())
                .accumulatedAt(history.getAccumulatedAt())
                .createdAt(history.getCreatedAt())
                .build();
    }
}

