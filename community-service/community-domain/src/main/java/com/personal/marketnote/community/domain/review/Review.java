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
    private Long reviewerId;
    private Long orderId;
    private Long productId;
    private Long pricePolicyId;
    private String selectedOptions;
    private Integer quantity;
    private String reviewerName;
    private Float score;
    private String content;
    private Boolean photoYn;
    private Boolean editedYn;
    private EntityStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long orderNum;

    public static Review of(
            Long reviewerId,
            Long orderId,
            Long productId,
            Long pricePolicyId,
            String selectedOptions,
            Integer quantity,
            String reviewerName,
            Float score,
            String content,
            Boolean photoYn
    ) {
        return Review.builder()
                .reviewerId(reviewerId)
                .orderId(orderId)
                .productId(productId)
                .pricePolicyId(pricePolicyId)
                .selectedOptions(selectedOptions)
                .quantity(quantity)
                .reviewerName(reviewerName)
                .score(score)
                .content(content)
                .photoYn(photoYn)
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static Review of(
            Long id,
            Long reviewerId,
            Long orderId,
            Long productId,
            Long pricePolicyId,
            String selectedOptions,
            Integer quantity,
            String reviewerName,
            Float score,
            String content,
            Boolean photoYn,
            Boolean editedYn,
            EntityStatus status,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            Long orderNum
    ) {
        return Review.builder()
                .id(id)
                .reviewerId(reviewerId)
                .orderId(orderId)
                .productId(productId)
                .pricePolicyId(pricePolicyId)
                .selectedOptions(selectedOptions)
                .quantity(quantity)
                .reviewerName(reviewerName)
                .score(score)
                .content(content)
                .photoYn(photoYn)
                .editedYn(editedYn)
                .status(status)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .orderNum(orderNum)
                .build();
    }
}
