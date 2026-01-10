package com.personal.marketnote.community.adapter.out.persistence.review.entity;

import com.personal.marketnote.common.adapter.out.persistence.audit.BaseOrderedGeneralEntity;
import com.personal.marketnote.community.domain.review.Review;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "review")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ReviewJpaEntity extends BaseOrderedGeneralEntity {
    @Column(name = "reviewer_id", nullable = false)
    private Long reviewerId;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "price_policy_id", nullable = false)
    private Long pricePolicyId;

    @Column(name = "selected_options", length = 127)
    private String selectedOptions;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "reviewer_name", nullable = false, length = 15)
    private String reviewerName;

    @Column(name = "score", nullable = false)
    private Float score;

    @Column(name = "content", nullable = false, length = 8192)
    private String content;

    @Column(name = "photo_yn", nullable = false)
    private Boolean photoYn;

    @Column(name = "edited_yn", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean editedYn;

    public static ReviewJpaEntity from(Review review) {
        return ReviewJpaEntity.builder()
                .reviewerId(review.getReviewerId())
                .orderId(review.getOrderId())
                .productId(review.getProductId())
                .pricePolicyId(review.getPricePolicyId())
                .selectedOptions(review.getSelectedOptions())
                .quantity(review.getQuantity())
                .reviewerName(review.getReviewerName())
                .score(review.getScore())
                .content(review.getContent())
                .photoYn(review.getPhotoYn())
                .build();
    }
}
