package com.personal.marketnote.community.adapter.out.persistence.review.repository;

import com.personal.marketnote.community.adapter.out.persistence.review.entity.ReviewReportJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewReportJpaRepository extends JpaRepository<ReviewReportJpaEntity, Long> {
    @Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
            FROM ReviewReportJpaEntity r
            WHERE r.id.reviewId = :reviewId
              AND r.id.reporterId = :reporterId
            """)
    boolean existsByReviewIdAndReporterId(@Param("reviewId") Long reviewId, @Param("reporterId") Long reporterId);

    @Query("""
            SELECT r
            FROM ReviewReportJpaEntity r
            WHERE r.id.reviewId = :reviewId
            ORDER BY r.createdAt DESC
            """)
    List<ReviewReportJpaEntity> findByReviewId(Long reviewId);
}
    