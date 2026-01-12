package com.personal.marketnote.community.adapter.out.persistence.review.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
    @Column(nullable = false, updatable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    public static ReviewReportJpaEntity from(ReviewReport report) {
        return ReviewReportJpaEntity.builder()
                .id(new ReviewReportId(report.getReviewId(), report.getReporterId()))
                .reason(report.getReason())
                .build();
    }

    public Long getReviewId() {
        return id.getReviewId();
    }

    public Long getReporterId() {
        return id.getReporterId();
    }
}


