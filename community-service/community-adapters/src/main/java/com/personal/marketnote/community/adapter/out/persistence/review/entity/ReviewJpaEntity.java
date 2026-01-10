package com.personal.marketnote.community.adapter.out.persistence.review.entity;

import com.personal.marketnote.common.adapter.out.persistence.audit.BaseGeneralEntity;
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
public class ReviewJpaEntity extends BaseGeneralEntity {
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "price_policy_id", nullable = false)
    private Long pricePolicyId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "score", nullable = false)
    private Float score;

    @Column(name = "content", nullable = false, length = 8192)
    private String content;

    @Column(name = "edited_yn", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean edited_yn;

    public static ReviewJpaEntity from(Review review) {
        return ReviewJpaEntity.builder()
                .orderId(review.getOrderId())
                .pricePolicyId(review.getPricePolicyId())
                .userId(review.getUserId())
                .score(review.getScore())
                .content(review.getContent())
                .build();
    }
}
