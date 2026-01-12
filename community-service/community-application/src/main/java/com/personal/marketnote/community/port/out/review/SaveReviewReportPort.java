package com.personal.marketnote.community.port.out.review;

import com.personal.marketnote.community.domain.review.ReviewReport;

public interface SaveReviewReportPort {
    void save(ReviewReport reviewReport);
}
