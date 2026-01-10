package com.personal.marketnote.community.domain.review;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Review {
    private Long id;
    private Long orderId;
    private Long pricePolicyId;
    private Long userId;
    private Float score;
    private String content;
    private Boolean edited_yn;
    private EntityStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static Review of(
            Long orderId,
            Long pricePolicyId,
            Long userId,
            Float score,
            String content
    ) {
        return Review.builder()
                .orderId(orderId)
                .pricePolicyId(pricePolicyId)
                .userId(userId)
                .score(score)
                .content(content)
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static Review of(
            Long id,
            Long orderId,
            Long pricePolicyId,
            Long userId,
            Float score,
            String content,
            EntityStatus status,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        return Review.builder()
                .id(id)
                .orderId(orderId)
                .pricePolicyId(pricePolicyId)
                .userId(userId)
                .score(score)
                .content(content)
                .status(status)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }
}
