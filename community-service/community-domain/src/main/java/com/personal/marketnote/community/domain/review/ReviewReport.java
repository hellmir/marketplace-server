package com.personal.marketnote.community.domain.review;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ReviewReport {
    private Long reviewId;
    private Long reporterId;
    private String reason;
    private LocalDateTime createdAt;

    public static ReviewReport of(Long reviewId, Long reporterId, String reason) {
        return ReviewReport.builder()
                .reviewId(reviewId)
                .reporterId(reporterId)
                .reason(reason)
                .build();
    }

    public static ReviewReport of(Long reviewId, Long reporterId, String reason, LocalDateTime createdAt) {
        return ReviewReport.builder()
                .reviewId(reviewId)
                .reporterId(reporterId)
                .reason(reason)
                .createdAt(createdAt)
                .build();
    }
}
