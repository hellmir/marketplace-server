package com.personal.marketnote.community.adapter.out.persistence.review.entity;

import com.personal.marketnote.community.domain.review.ReviewReport;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "review_report")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ReviewReportJpaEntity {
    @EmbeddedId
    private ReviewReportId id;

    @Column(name = "reason", nullable = false, length = 2047)
    private String reason;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public static ReviewReportJpaEntity from(ReviewReport report) {
        return ReviewReportJpaEntity.builder()
                .id(new ReviewReportId(report.getReviewId(), report.getReporterId()))
                .reason(report.getReason())
                .build();
    }
}


