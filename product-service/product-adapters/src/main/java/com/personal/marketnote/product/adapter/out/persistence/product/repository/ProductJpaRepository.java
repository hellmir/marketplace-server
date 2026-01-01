package com.personal.marketnote.product.adapter.out.persistence.product.repository;

import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductJpaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {
    boolean existsByIdAndSellerId(Long productId, Long sellerId);

    @Query("""
            SELECT p
            FROM ProductJpaEntity p
            WHERE 1 = 1
              AND EXISTS (
                SELECT 1
                FROM com.personal.marketnote.product.adapter.out.persistence.productcategory.entity.ProductCategoryJpaEntity pc
                WHERE 1 = 1
                    AND pc.productId = p.id
                    AND pc.categoryId = :categoryId
              )
            ORDER BY p.orderNum ASC
            """)
    List<ProductJpaEntity> findAllByCategoryIdOrderByOrderNumAsc(@Param("categoryId") Long categoryId);

    @Query("""
            SELECT p
            FROM ProductJpaEntity p
            WHERE 1 = 1
              AND p.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
              AND (
                    :pattern IS NULL
                 OR (:searchTarget = 'name' AND p.name LIKE :pattern)
                 OR (:searchTarget = 'brandName' AND p.brandName LIKE :pattern)
                 OR (:searchTarget <> 'name' AND :searchTarget <> 'brandName' AND (p.name LIKE :pattern OR p.brandName LIKE :pattern))
              )
              AND (
                    (:sortProperty = 'orderNum' AND p.orderNum > :cursor)
                 OR (:sortProperty = 'popularity' AND p.sales > :cursor)
                 OR (:sortProperty = 'currentPrice' AND p.orderNum > :cursor)
                 OR (:sortProperty = 'accumulatedPoint' AND p.orderNum > :cursor)
              )
            ORDER BY
                CASE WHEN :sortProperty = 'orderNum' THEN p.orderNum END ASC,
                CASE WHEN :sortProperty = 'popularity' THEN p.sales END ASC,
                CASE WHEN :sortProperty = 'currentPrice' THEN p.orderNum END ASC,
                CASE WHEN :sortProperty = 'accumulatedPoint' THEN p.orderNum END ASC,
                p.orderNum ASC
            """)
    List<ProductJpaEntity> findAllActiveByCursorAsc(
            @Param("cursor") Long cursor,
            @Param("pageable") Pageable pageable,
            @Param("sortProperty") String sortProperty,
            @Param("searchTarget") String searchTarget,
            @Param("pattern") String pattern);

    @Query("""
            SELECT p
            FROM ProductJpaEntity p
            WHERE 1 = 1
              AND p.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
              AND (
                    :pattern IS NULL
                 OR (:searchTarget = 'name' AND p.name LIKE :pattern)
                 OR (:searchTarget = 'brandName' AND p.brandName LIKE :pattern)
                 OR (:searchTarget <> 'name' AND :searchTarget <> 'brandName' AND (p.name LIKE :pattern OR p.brandName LIKE :pattern))
              )
              AND (
                    (:sortProperty = 'orderNum' AND p.orderNum < :cursor)
                 OR (:sortProperty = 'popularity' AND p.sales < :cursor)
                 OR (:sortProperty = 'currentPrice' AND p.orderNum < :cursor)
                 OR (:sortProperty = 'accumulatedPoint' AND p.orderNum < :cursor)
              )
            ORDER BY
                CASE WHEN :sortProperty = 'orderNum' THEN p.orderNum END DESC,
                CASE WHEN :sortProperty = 'popularity' THEN p.sales END DESC,
                CASE WHEN :sortProperty = 'currentPrice' THEN p.orderNum END DESC,
                CASE WHEN :sortProperty = 'accumulatedPoint' THEN p.orderNum END DESC,
                p.orderNum DESC
            """)
    List<ProductJpaEntity> findAllActiveByCursorDesc(
            @Param("cursor") Long cursor,
            @Param("pageable") Pageable pageable,
            @Param("sortProperty") String sortProperty,
            @Param("searchTarget") String searchTarget,
            @Param("pattern") String pattern
    );

    @Query("""
            SELECT p
            FROM ProductJpaEntity p
            WHERE 1 = 1
              AND p.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
              AND EXISTS (
                SELECT 1
                FROM com.personal.marketnote.product.adapter.out.persistence.productcategory.entity.ProductCategoryJpaEntity pc
                WHERE 1 = 1
                    AND pc.productId = p.id
                    AND pc.categoryId = :categoryId
              )
              AND (
                    :pattern IS NULL
                 OR (:searchTarget = 'name' AND p.name LIKE :pattern)
                 OR (:searchTarget = 'brandName' AND p.brandName LIKE :pattern)
                 OR (:searchTarget <> 'name' AND :searchTarget <> 'brandName' AND (p.name LIKE :pattern OR p.brandName LIKE :pattern))
              )
              AND (
                    (:sortProperty = 'orderNum' AND p.orderNum > :cursor)
                 OR (:sortProperty = 'popularity' AND p.sales > :cursor)
                 OR (:sortProperty = 'currentPrice' AND p.orderNum > :cursor)
                 OR (:sortProperty = 'accumulatedPoint' AND p.orderNum > :cursor)
              )
            ORDER BY
                CASE WHEN :sortProperty = 'orderNum' THEN p.orderNum END ASC,
                CASE WHEN :sortProperty = 'popularity' THEN p.sales END ASC,
                CASE WHEN :sortProperty = 'currentPrice' THEN p.orderNum END ASC,
                CASE WHEN :sortProperty = 'accumulatedPoint' THEN p.orderNum END ASC,
                p.orderNum ASC
            """)
    List<ProductJpaEntity> findAllActiveByCategoryIdCursorAsc(
            @Param("categoryId") Long categoryId,
            @Param("cursor") Long cursor,
            @Param("pageable") Pageable pageable,
            @Param("sortProperty") String sortProperty,
            @Param("searchTarget") String searchTarget,
            @Param("pattern") String pattern);

    @Query("""
            SELECT p
            FROM ProductJpaEntity p
            WHERE 1 = 1
              AND p.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
              AND EXISTS (
                SELECT 1
                FROM com.personal.marketnote.product.adapter.out.persistence.productcategory.entity.ProductCategoryJpaEntity pc
                WHERE 1 = 1
                    AND pc.productId = p.id
                    AND pc.categoryId = :categoryId
              )
              AND (
                    :pattern IS NULL
                 OR (:searchTarget = 'name' AND p.name LIKE :pattern)
                 OR (:searchTarget = 'brandName' AND p.brandName LIKE :pattern)
                 OR (:searchTarget <> 'name' AND :searchTarget <> 'brandName' AND (p.name LIKE :pattern OR p.brandName LIKE :pattern))
              )
              AND (
                    (:sortProperty = 'orderNum' AND p.orderNum < :cursor)
                 OR (:sortProperty = 'popularity' AND p.sales < :cursor)
                 OR (:sortProperty = 'currentPrice' AND p.orderNum < :cursor)
                 OR (:sortProperty = 'accumulatedPoint' AND p.orderNum < :cursor)
              )
            ORDER BY
                CASE WHEN :sortProperty = 'orderNum' THEN p.orderNum END DESC,
                CASE WHEN :sortProperty = 'popularity' THEN p.sales END DESC,
                CASE WHEN :sortProperty = 'currentPrice' THEN p.orderNum END DESC,
                CASE WHEN :sortProperty = 'accumulatedPoint' THEN p.orderNum END DESC,
                p.orderNum DESC
            """)
    List<ProductJpaEntity> findAllActiveByCategoryIdCursorDesc(
            @Param("categoryId") Long categoryId,
            @Param("cursor") Long cursor,
            @Param("pageable") Pageable pageable,
            @Param("sortProperty") String sortProperty,
            @Param("searchTarget") String searchTarget,
            @Param("pattern") String pattern);

    @Query("""
            SELECT COUNT(p)
            FROM ProductJpaEntity p
            WHERE 1 = 1
              AND p.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
              AND (
                    :pattern IS NULL
                 OR (:searchTarget = 'name' AND p.name LIKE :pattern)
                 OR (:searchTarget = 'brandName' AND p.brandName LIKE :pattern)
                 OR (:searchTarget <> 'name' AND :searchTarget <> 'brandName' AND (p.name LIKE :pattern OR p.brandName LIKE :pattern))
              )
            """)
    long countActive(@Param("searchTarget") String searchTarget, @Param("pattern") String pattern);

    @Query("""
            SELECT COUNT(p)
            FROM ProductJpaEntity p
            WHERE 1 = 1
              AND p.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
              AND EXISTS (
                SELECT 1
                FROM com.personal.marketnote.product.adapter.out.persistence.productcategory.entity.ProductCategoryJpaEntity pc
                WHERE 1 = 1
                    AND pc.productId = p.id
                    AND pc.categoryId = :categoryId
              )
              AND (
                    :pattern IS NULL
                 OR (:searchTarget = 'name' AND p.name LIKE :pattern)
                 OR (:searchTarget = 'brandName' AND p.brandName LIKE :pattern)
                 OR (:searchTarget <> 'name' AND :searchTarget <> 'brandName' AND (p.name LIKE :pattern OR p.brandName LIKE :pattern))
              )
            """)
    long countActiveByCategoryId(
            @Param("categoryId") Long categoryId,
            @Param("searchTarget") String searchTarget,
            @Param("pattern") String pattern
    );
}
