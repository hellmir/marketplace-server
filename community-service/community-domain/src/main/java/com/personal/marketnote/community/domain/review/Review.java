package com.personal.marketnote.community.domain.review;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import static com.personal.marketnote.common.utility.CharacterConstant.WILD_CARD;

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
    private Float rating;
    private String content;
    private Boolean photoYn;
    private Boolean editedYn;
    private List<Long> likeUserIds;
    private Boolean isUserLiked;
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
            Float rating,
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
                .reviewerName(mask(reviewerName))
                .rating(round(rating))
                .content(content)
                .photoYn(photoYn)
                .status(EntityStatus.ACTIVE)
                .build();
    }

    private static String mask(String value) {
        int length = value.length();
        String firstChar = value.substring(0, 1);
        String lastChar = value.substring(length - 1, length);

        if (length < 3) {
            return firstChar + WILD_CARD;
        }

        return firstChar + WILD_CARD + lastChar;
    }

    private static Float round(Float value) {
        return BigDecimal.valueOf(value)
                .setScale(0, RoundingMode.HALF_UP)
                .floatValue();
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
            Float rating,
            String content,
            Boolean photoYn,
            Boolean editedYn,
            List<Long> likeUserIds,
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
                .rating(rating)
                .content(content)
                .photoYn(photoYn)
                .editedYn(editedYn)
                .likeUserIds(likeUserIds)
                .status(status)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .orderNum(orderNum)
                .build();
    }

    public void updateIsUserLiked(Long userId) {
        isUserLiked = likeUserIds.contains(userId);
    }
}
