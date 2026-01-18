package com.personal.marketnote.reward.domain.point;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class UserPointHistory {
    private Long id;
    private Long userId;
    private Long amount;
    private Boolean isReflected;
    private UserPointSourceType sourceType;
    private Long sourceId;
    private String reason;
    private LocalDateTime accumulatedAt;
    private LocalDateTime createdAt;

    public static UserPointHistory from(UserPointHistoryCreateState state) {
        return UserPointHistory.builder()
                .userId(state.getUserId())
                .amount(state.getAmount())
                .isReflected(state.getIsReflected())
                .sourceType(state.getSourceType())
                .sourceId(state.getSourceId())
                .reason(state.getReason())
                .accumulatedAt(state.getAccumulatedAt())
                .build();
    }

    public static UserPointHistory from(UserPointHistorySnapshotState state) {
        return UserPointHistory.builder()
                .id(state.getId())
                .userId(state.getUserId())
                .amount(state.getAmount())
                .isReflected(state.getIsReflected())
                .sourceType(state.getSourceType())
                .sourceId(state.getSourceId())
                .reason(state.getReason())
                .accumulatedAt(state.getAccumulatedAt())
                .createdAt(state.getCreatedAt())
                .build();
    }
}
