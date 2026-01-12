package com.personal.marketnote.community.domain.review;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ProductReviewAggregate {
    private Long productId;
    private int totalCount;
    private int fivePointCount;
    private int fourPointCount;
    private int threePointCount;
    private int twoPointCount;
    private int onePointCount;
    private float averageRating;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ProductReviewAggregate from(ProductReviewAggregateSnapshotState state) {
        return ProductReviewAggregate.builder()
                .productId(state.getProductId())
                .totalCount(state.getTotalCount())
                .fivePointCount(state.getFivePointCount())
                .fourPointCount(state.getFourPointCount())
                .threePointCount(state.getThreePointCount())
                .twoPointCount(state.getTwoPointCount())
                .onePointCount(state.getOnePointCount())
                .averageRating(state.getAverageRating())
                .createdAt(state.getCreatedAt())
                .modifiedAt(state.getModifiedAt())
                .build();
    }

    public static ProductReviewAggregate from(Review review) {
        ProductReviewAggregate productReviewAggregate = ProductReviewAggregate.builder()
                .productId(review.getProductId())
                .build();
        productReviewAggregate.averageRating = review.getRating();
        addPoint(productReviewAggregate, review.getRating().intValue());

        return productReviewAggregate;
    }

    public static void changePoint(ProductReviewAggregate productReviewAggregate, Float previousRating, Float newRating) {
        reducePoint(productReviewAggregate, previousRating.intValue());
        addPoint(productReviewAggregate, newRating.intValue());
    }

    public static void reducePoint(ProductReviewAggregate productReviewAggregate, int point) {
        --productReviewAggregate.totalCount;

        if (RatingPoint.isFive(point)) {
            --productReviewAggregate.fivePointCount;
            return;
        }

        if (RatingPoint.isFour(point)) {
            --productReviewAggregate.fourPointCount;
            return;
        }

        if (RatingPoint.isThree(point)) {
            --productReviewAggregate.threePointCount;
            return;
        }

        if (RatingPoint.isTwo(point)) {
            --productReviewAggregate.twoPointCount;
            return;
        }

        if (RatingPoint.isOne(point)) {
            --productReviewAggregate.onePointCount;
            return;
        }

        throw new IllegalArgumentException("평점은 1 이상 5 이하의 정수만 가능합니다. 전송된 평점: " + point);
    }

    public static void addPoint(ProductReviewAggregate productReviewAggregate, int point) {
        ++productReviewAggregate.totalCount;

        if (RatingPoint.isFive(point)) {
            ++productReviewAggregate.fivePointCount;
            return;
        }

        if (RatingPoint.isFour(point)) {
            ++productReviewAggregate.fourPointCount;
            return;
        }

        if (RatingPoint.isThree(point)) {
            ++productReviewAggregate.threePointCount;
            return;
        }

        if (RatingPoint.isTwo(point)) {
            ++productReviewAggregate.twoPointCount;
            return;
        }

        if (RatingPoint.isOne(point)) {
            ++productReviewAggregate.onePointCount;
            return;
        }

        throw new IllegalArgumentException("평점은 1 이상 5 이하의 정수만 가능합니다. 전송된 평점: " + point);
    }

    public void computeAverageRating(int point) {
        int previousCount = totalCount - 1;
        averageRating = (previousCount * averageRating + point) / totalCount;
    }

    public void computeAverageRating(int previousPoint, int newPoint) {
        averageRating = (totalCount * averageRating + newPoint - previousPoint) / totalCount;
    }
}

