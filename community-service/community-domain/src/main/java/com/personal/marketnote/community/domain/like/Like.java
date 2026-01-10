package com.personal.marketnote.community.domain.like;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Like {
    private LikeTargetType targetType;
    private Long targetId;
    private Long userId;
    private EntityStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static Like of(LikeTargetType targetType, Long targetId, Long userId) {
        return Like.builder()
                .targetType(targetType)
                .targetId(targetId)
                .userId(userId)
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static Like of(
            LikeTargetType targetType,
            Long targetId,
            Long userId,
            EntityStatus status,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        return Like.builder()
                .targetType(targetType)
                .targetId(targetId)
                .userId(userId)
                .status(status)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }

    public void revert() {
        status = EntityStatus.from(!isLiked());
    }

    public boolean isLiked() {
        return status.isActive();
    }
}
