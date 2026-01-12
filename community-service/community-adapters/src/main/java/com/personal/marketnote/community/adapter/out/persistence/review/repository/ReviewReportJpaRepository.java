package com.personal.marketnote.community.adapter.out.persistence.review.repository;

import com.personal.marketnote.community.adapter.out.persistence.review.entity.ReviewReportJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewReportJpaRepository extends JpaRepository<ReviewReportJpaEntity, Long> {
    @Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
            FROM ReviewReportJpaEntity r
            WHERE r.id.reviewId = :reviewId
              AND r.id.reporterId = :reporterId
            """)
    boolean existsByReviewIdAndReporterId(@Param("reviewId") Long reviewId, @Param("reporterId") Long reporterId);
}
