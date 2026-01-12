package com.personal.marketnote.community.adapter.out.persistence.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReportId implements Serializable {
    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "reporter_id", nullable = false)
    private Long reporterId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReviewReportId that = (ReviewReportId) o;
        return Objects.equals(reviewId, that.reviewId)
                && Objects.equals(reporterId, that.reporterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, reporterId);
    }
}


