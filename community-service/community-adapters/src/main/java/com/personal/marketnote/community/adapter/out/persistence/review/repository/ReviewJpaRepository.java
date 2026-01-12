package com.personal.marketnote.community.adapter.out.persistence.review.repository;

import com.personal.marketnote.community.adapter.out.persistence.review.entity.ReviewJpaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewJpaRepository extends JpaRepository<ReviewJpaEntity, Long> {
    boolean existsByOrderIdAndPricePolicyId(Long orderId, Long pricePolicyId);

    @Query("""
            SELECT r
            FROM ReviewJpaEntity r
            WHERE r.productId = :productId
              AND (
                    :cursor IS NULL
                 OR NOT EXISTS (
                    SELECT 1
                    FROM ReviewJpaEntity r0
                    WHERE r0.id = :cursor
                 )
                 OR (
                        (:sortProperty = 'id' AND r.id < :cursor)
                     OR (:sortProperty = 'orderNum' AND (
                            r.orderNum < (SELECT r2.orderNum FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                         OR (r.orderNum = (SELECT r2.orderNum FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                             AND r.id < :cursor)
                       ))
                 )
              )
            ORDER BY
                CASE WHEN :sortProperty = 'orderNum' THEN r.orderNum END DESC,
                r.id DESC
            """)
    List<ReviewJpaEntity> findProductReviewsByCursor(
            @Param("productId") Long productId,
            @Param("cursor") Long cursor,
            Pageable pageable,
            @Param("sortProperty") String sortProperty
    );

    @Query("""
            SELECT r
            FROM ReviewJpaEntity r
            WHERE 1 = 1
              AND r.productId = :productId
              AND r.isPhoto = TRUE
              AND (
                    :cursor IS NULL
                 OR NOT EXISTS (
                    SELECT 1
                    FROM ReviewJpaEntity r0
                    WHERE r0.id = :cursor
                 )
                 OR (
                        (:sortProperty = 'id' AND r.id < :cursor)
                     OR (:sortProperty = 'orderNum' AND (
                            r.orderNum < (SELECT r2.orderNum FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                         OR (r.orderNum = (SELECT r2.orderNum FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                             AND r.id < :cursor)
                       ))
                 )
              )
            ORDER BY
                CASE WHEN :sortProperty = 'orderNum' THEN r.orderNum END DESC,
                r.id DESC
            """)
    List<ReviewJpaEntity> findProductPhotoReviewsByCursor(
            @Param("productId") Long productId,
            @Param("cursor") Long cursor,
            Pageable pageable,
            @Param("sortProperty") String sortProperty
    );

    @Query("""
            SELECT COUNT(r)
            FROM ReviewJpaEntity r
            WHERE 1 = 1
              AND r.productId = :productId
              AND r.isPhoto = :isPhoto
            """)
    long countByProductIdAndIsPhoto(@Param("productId") Long productId, @Param("isPhoto") Boolean isPhoto);

    @Query("""
            SELECT COUNT(r)
            FROM ReviewJpaEntity r
            WHERE 1 = 1
              AND r.productId = :productId
            """)
    long countByProductId(@Param("productId") Long productId);
}
