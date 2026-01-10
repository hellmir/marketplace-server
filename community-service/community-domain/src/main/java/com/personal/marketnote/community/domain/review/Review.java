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

    public static Review from(ReviewCreateState state) {
        return Review.builder()
                .reviewerId(state.getReviewerId())
                .orderId(state.getOrderId())
                .productId(state.getProductId())
                .pricePolicyId(state.getPricePolicyId())
                .selectedOptions(state.getSelectedOptions())
                .quantity(state.getQuantity())
                .reviewerName(mask(state.getReviewerName()))
                .rating(round(state.getRating()))
                .content(state.getContent())
                .photoYn(state.getPhotoYn())
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

    public static Review from(ReviewSnapshotState state) {
        return Review.builder()
                .id(state.getId())
                .reviewerId(state.getReviewerId())
                .orderId(state.getOrderId())
                .productId(state.getProductId())
                .pricePolicyId(state.getPricePolicyId())
                .selectedOptions(state.getSelectedOptions())
                .quantity(state.getQuantity())
                .reviewerName(state.getReviewerName())
                .rating(state.getRating())
                .content(state.getContent())
                .photoYn(state.getPhotoYn())
                .editedYn(state.getEditedYn())
                .likeUserIds(state.getLikeUserIds())
                .isUserLiked(state.getIsUserLiked())
                .status(state.getStatus())
                .createdAt(state.getCreatedAt())
                .modifiedAt(state.getModifiedAt())
                .orderNum(state.getOrderNum())
                .build();
    }

    public void updateIsUserLiked(Long userId) {
        isUserLiked = likeUserIds.contains(userId);
    }
}
