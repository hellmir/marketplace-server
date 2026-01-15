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
                        (:sortProperty = 'id' AND (
                                (:isAsc = TRUE AND r.id > :cursor)
                             OR (:isAsc = FALSE AND r.id < :cursor)
                        ))
                     OR (:sortProperty = 'orderNum' AND (
                            (:isAsc = TRUE AND (
                                    r.orderNum > (SELECT r2.orderNum FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                 OR (r.orderNum = (SELECT r2.orderNum FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                     AND r.id > :cursor)
                            ))
                         OR (:isAsc = FALSE AND (
                                    r.orderNum < (SELECT r2.orderNum FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                 OR (r.orderNum = (SELECT r2.orderNum FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                     AND r.id < :cursor)
                            ))
                       ))
                     OR (:sortProperty = 'likeCount' AND (
                            (:isAsc = TRUE AND (
                                    (SELECT COUNT(l) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l
                                     WHERE l.id.targetType = 'REVIEW' AND l.id.targetId = r.id)
                                        > (SELECT COUNT(l2) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l2
                                           WHERE l2.id.targetType = 'REVIEW' AND l2.id.targetId = :cursor)
                                 OR ((SELECT COUNT(l) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l
                                      WHERE l.id.targetType = 'REVIEW' AND l.id.targetId = r.id)
                                        = (SELECT COUNT(l2) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l2
                                           WHERE l2.id.targetType = 'REVIEW' AND l2.id.targetId = :cursor)
                                     AND r.id > :cursor)
                            ))
                         OR (:isAsc = FALSE AND (
                                    (SELECT COUNT(l) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l
                                     WHERE l.id.targetType = 'REVIEW' AND l.id.targetId = r.id)
                                        < (SELECT COUNT(l2) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l2
                                           WHERE l2.id.targetType = 'REVIEW' AND l2.id.targetId = :cursor)
                                 OR ((SELECT COUNT(l) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l
                                      WHERE l.id.targetType = 'REVIEW' AND l.id.targetId = r.id)
                                        = (SELECT COUNT(l2) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l2
                                           WHERE l2.id.targetType = 'REVIEW' AND l2.id.targetId = :cursor)
                                     AND r.id < :cursor)
                            ))
                       ))
                     OR (:sortProperty = 'rating' AND (
                            (:isAsc = TRUE AND (
                                    r.rating > (SELECT r2.rating FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                 OR (r.rating = (SELECT r2.rating FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                     AND r.id > :cursor)
                            ))
                         OR (:isAsc = FALSE AND (
                                    r.rating < (SELECT r2.rating FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                 OR (r.rating = (SELECT r2.rating FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                     AND r.id < :cursor)
                            ))
                       ))
                 )
              )
            ORDER BY
                CASE WHEN :sortProperty = 'orderNum' AND :isAsc = TRUE THEN r.orderNum END ASC,
                CASE WHEN :sortProperty = 'orderNum' AND :isAsc = FALSE THEN r.orderNum END DESC,
                CASE WHEN :sortProperty = 'likeCount' AND :isAsc = TRUE THEN (
                    SELECT COUNT(l) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l
                    WHERE l.id.targetType = 'REVIEW' AND l.id.targetId = r.id
                ) END ASC,
                CASE WHEN :sortProperty = 'likeCount' AND :isAsc = FALSE THEN (
                    SELECT COUNT(l) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l
                    WHERE l.id.targetType = 'REVIEW' AND l.id.targetId = r.id
                ) END DESC,
                CASE WHEN :sortProperty = 'rating' AND :isAsc = TRUE THEN r.rating END ASC,
                CASE WHEN :sortProperty = 'rating' AND :isAsc = FALSE THEN r.rating END DESC,
                CASE WHEN :sortProperty = 'id' AND :isAsc = TRUE THEN r.id END ASC,
                CASE WHEN :sortProperty = 'id' AND :isAsc = FALSE THEN r.id END DESC,
                CASE WHEN :isAsc = TRUE THEN r.id END ASC,
                CASE WHEN :isAsc = FALSE THEN r.id END DESC
            """)
    List<ReviewJpaEntity> findProductReviewsByCursor(
            @Param("productId") Long productId,
            @Param("cursor") Long cursor,
            Pageable pageable,
            @Param("sortProperty") String sortProperty,
            @Param("isAsc") Boolean isAsc
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
                        (:sortProperty = 'id' AND (
                                (:isAsc = TRUE AND r.id > :cursor)
                             OR (:isAsc = FALSE AND r.id < :cursor)
                        ))
                     OR (:sortProperty = 'orderNum' AND (
                            (:isAsc = TRUE AND (
                                    r.orderNum > (SELECT r2.orderNum FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                 OR (r.orderNum = (SELECT r2.orderNum FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                     AND r.id > :cursor)
                            ))
                         OR (:isAsc = FALSE AND (
                                    r.orderNum < (SELECT r2.orderNum FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                 OR (r.orderNum = (SELECT r2.orderNum FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                     AND r.id < :cursor)
                            ))
                       ))
                     OR (:sortProperty = 'likeCount' AND (
                            (:isAsc = TRUE AND (
                                    (SELECT COUNT(l) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l
                                     WHERE l.id.targetType = 'REVIEW' AND l.id.targetId = r.id)
                                        > (SELECT COUNT(l2) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l2
                                           WHERE l2.id.targetType = 'REVIEW' AND l2.id.targetId = :cursor)
                                 OR ((SELECT COUNT(l) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l
                                      WHERE l.id.targetType = 'REVIEW' AND l.id.targetId = r.id)
                                        = (SELECT COUNT(l2) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l2
                                           WHERE l2.id.targetType = 'REVIEW' AND l2.id.targetId = :cursor)
                                     AND r.id > :cursor)
                            ))
                         OR (:isAsc = FALSE AND (
                                    (SELECT COUNT(l) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l
                                     WHERE l.id.targetType = 'REVIEW' AND l.id.targetId = r.id)
                                        < (SELECT COUNT(l2) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l2
                                           WHERE l2.id.targetType = 'REVIEW' AND l2.id.targetId = :cursor)
                                 OR ((SELECT COUNT(l) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l
                                      WHERE l.id.targetType = 'REVIEW' AND l.id.targetId = r.id)
                                        = (SELECT COUNT(l2) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l2
                                           WHERE l2.id.targetType = 'REVIEW' AND l2.id.targetId = :cursor)
                                     AND r.id < :cursor)
                            ))
                       ))
                     OR (:sortProperty = 'rating' AND (
                            (:isAsc = TRUE AND (
                                    r.rating > (SELECT r2.rating FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                 OR (r.rating = (SELECT r2.rating FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                     AND r.id > :cursor)
                            ))
                         OR (:isAsc = FALSE AND (
                                    r.rating < (SELECT r2.rating FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                 OR (r.rating = (SELECT r2.rating FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                     AND r.id < :cursor)
                            ))
                       ))
                 )
              )
            ORDER BY
                CASE WHEN :sortProperty = 'orderNum' AND :isAsc = TRUE THEN r.orderNum END ASC,
                CASE WHEN :sortProperty = 'orderNum' AND :isAsc = FALSE THEN r.orderNum END DESC,
                CASE WHEN :sortProperty = 'likeCount' AND :isAsc = TRUE THEN (
                    SELECT COUNT(l) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l
                    WHERE l.id.targetType = 'REVIEW' AND l.id.targetId = r.id
                ) END ASC,
                CASE WHEN :sortProperty = 'likeCount' AND :isAsc = FALSE THEN (
                    SELECT COUNT(l) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l
                    WHERE l.id.targetType = 'REVIEW' AND l.id.targetId = r.id
                ) END DESC,
                CASE WHEN :sortProperty = 'rating' AND :isAsc = TRUE THEN r.rating END ASC,
                CASE WHEN :sortProperty = 'rating' AND :isAsc = FALSE THEN r.rating END DESC,
                CASE WHEN :sortProperty = 'id' AND :isAsc = TRUE THEN r.id END ASC,
                CASE WHEN :sortProperty = 'id' AND :isAsc = FALSE THEN r.id END DESC,
                CASE WHEN :isAsc = TRUE THEN r.id END ASC,
                CASE WHEN :isAsc = FALSE THEN r.id END DESC
            """)
    List<ReviewJpaEntity> findProductPhotoReviewsByCursor(
            @Param("productId") Long productId,
            @Param("cursor") Long cursor,
            Pageable pageable,
            @Param("sortProperty") String sortProperty,
            @Param("isAsc") Boolean isAsc
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

    boolean existsByIdAndReviewerId(Long id, Long reviewerId);

    @Query("""
            SELECT r
            FROM ReviewJpaEntity r
            WHERE r.reviewerId = :userId
              AND (
                    :cursor IS NULL
                 OR NOT EXISTS (
                    SELECT 1
                    FROM ReviewJpaEntity r0
                    WHERE r0.id = :cursor
                 )
                 OR (
                        (:sortProperty = 'id' AND (
                                (:isAsc = TRUE AND r.id > :cursor)
                             OR (:isAsc = FALSE AND r.id < :cursor)
                        ))
                     OR (:sortProperty = 'orderNum' AND (
                            (:isAsc = TRUE AND (
                                    r.orderNum > (SELECT r2.orderNum FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                 OR (r.orderNum = (SELECT r2.orderNum FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                     AND r.id > :cursor)
                            ))
                         OR (:isAsc = FALSE AND (
                                    r.orderNum < (SELECT r2.orderNum FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                 OR (r.orderNum = (SELECT r2.orderNum FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                     AND r.id < :cursor)
                            ))
                       ))
                     OR (:sortProperty = 'likeCount' AND (
                            (:isAsc = TRUE AND (
                                    (SELECT COUNT(l) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l
                                     WHERE l.id.targetType = 'REVIEW' AND l.id.targetId = r.id)
                                        > (SELECT COUNT(l2) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l2
                                           WHERE l2.id.targetType = 'REVIEW' AND l2.id.targetId = :cursor)
                                 OR ((SELECT COUNT(l) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l
                                      WHERE l.id.targetType = 'REVIEW' AND l.id.targetId = r.id)
                                        = (SELECT COUNT(l2) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l2
                                           WHERE l2.id.targetType = 'REVIEW' AND l2.id.targetId = :cursor)
                                     AND r.id > :cursor)
                            ))
                         OR (:isAsc = FALSE AND (
                                    (SELECT COUNT(l) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l
                                     WHERE l.id.targetType = 'REVIEW' AND l.id.targetId = r.id)
                                        < (SELECT COUNT(l2) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l2
                                           WHERE l2.id.targetType = 'REVIEW' AND l2.id.targetId = :cursor)
                                 OR ((SELECT COUNT(l) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l
                                      WHERE l.id.targetType = 'REVIEW' AND l.id.targetId = r.id)
                                        = (SELECT COUNT(l2) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l2
                                           WHERE l2.id.targetType = 'REVIEW' AND l2.id.targetId = :cursor)
                                     AND r.id < :cursor)
                            ))
                       ))
                     OR (:sortProperty = 'rating' AND (
                            (:isAsc = TRUE AND (
                                    r.rating > (SELECT r2.rating FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                 OR (r.rating = (SELECT r2.rating FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                     AND r.id > :cursor)
                            ))
                         OR (:isAsc = FALSE AND (
                                    r.rating < (SELECT r2.rating FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                 OR (r.rating = (SELECT r2.rating FROM ReviewJpaEntity r2 WHERE r2.id = :cursor)
                                     AND r.id < :cursor)
                            ))
                       ))
                 )
              )
            ORDER BY
                CASE WHEN :sortProperty = 'orderNum' AND :isAsc = TRUE THEN r.orderNum END ASC,
                CASE WHEN :sortProperty = 'orderNum' AND :isAsc = FALSE THEN r.orderNum END DESC,
                CASE WHEN :sortProperty = 'likeCount' AND :isAsc = TRUE THEN (
                    SELECT COUNT(l) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l
                    WHERE l.id.targetType = 'REVIEW' AND l.id.targetId = r.id
                ) END ASC,
                CASE WHEN :sortProperty = 'likeCount' AND :isAsc = FALSE THEN (
                    SELECT COUNT(l) FROM com.personal.marketnote.community.adapter.out.persistence.like.entity.LikeJpaEntity l
                    WHERE l.id.targetType = 'REVIEW' AND l.id.targetId = r.id
                ) END DESC,
                CASE WHEN :sortProperty = 'rating' AND :isAsc = TRUE THEN r.rating END ASC,
                CASE WHEN :sortProperty = 'rating' AND :isAsc = FALSE THEN r.rating END DESC,
                CASE WHEN :sortProperty = 'id' AND :isAsc = TRUE THEN r.id END ASC,
                CASE WHEN :sortProperty = 'id' AND :isAsc = FALSE THEN r.id END DESC,
                CASE WHEN :isAsc = TRUE THEN r.id END ASC,
                CASE WHEN :isAsc = FALSE THEN r.id END DESC
            """)
    List<ReviewJpaEntity> findUserReviewsByCursor(
            @Param("userId") Long userId,
            @Param("cursor") Long cursor,
            Pageable pageable,
            @Param("sortProperty") String sortProperty,
            @Param("isAsc") Boolean isAsc
    );

    @Query("""
            SELECT COUNT(r)
            FROM ReviewJpaEntity r
            WHERE 1 = 1
              AND r.reviewerId = :reviewerId
            """)
    long countByReviewerId(@Param("reviewerId") Long reviewerId);
}
