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

    public static Like from(LikeCreateState state) {
        return Like.builder()
                .targetType(state.getTargetType())
                .targetId(state.getTargetId())
                .userId(state.getUserId())
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static Like from(LikeSnapshotState state) {
        return Like.builder()
                .targetType(state.getTargetType())
                .targetId(state.getTargetId())
                .userId(state.getUserId())
                .status(state.getStatus())
                .createdAt(state.getCreatedAt())
                .modifiedAt(state.getModifiedAt())
                .build();
    }

    public boolean isStatusChanged(boolean isLiked) {
        if (isLiked) {
            return status.isInactive();
        }

        return status.isActive();
    }

    public void revert() {
        status = EntityStatus.from(!isLiked());
    }

    public boolean isLiked() {
        return status.isActive();
    }
}
