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
              LEFT JOIN com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp
                ON pp.productJpaEntity = p
               AND pp.id = (
                    SELECT MAX(pp2.id)
                    FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp2
                    WHERE pp2.productJpaEntity = p
               )
            WHERE 1 = 1
              AND p.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
              AND (
                    :pattern IS NULL
                 OR (:searchTarget = 'name' AND p.name LIKE :pattern)
                 OR (:searchTarget = 'brandName' AND p.brandName LIKE :pattern)
                 OR (:searchTarget <> 'name' AND :searchTarget <> 'brandName' AND (p.name LIKE :pattern OR p.brandName LIKE :pattern))
              )
              AND (
                    :cursor IS NULL
                 OR NOT EXISTS (SELECT 1 FROM ProductJpaEntity p0 WHERE p0.id = :cursor)
                 OR (
                        (:sortProperty = 'orderNum' AND (
                              p.orderNum > (SELECT p2.orderNum FROM ProductJpaEntity p2 WHERE p2.id = :cursor)
                           OR (p.orderNum = (SELECT p2.orderNum FROM ProductJpaEntity p2 WHERE p2.id = :cursor) AND p.id > :cursor)
                        ))
                     OR (:sortProperty = 'popularity' AND (
                              p.sales > (SELECT p2.sales FROM ProductJpaEntity p2 WHERE p2.id = :cursor)
                           OR (p.sales = (SELECT p2.sales FROM ProductJpaEntity p2 WHERE p2.id = :cursor) AND p.id > :cursor)
                        ))
                     OR (:sortProperty = 'currentPrice' AND (
                              COALESCE(pp.currentPrice, 0) > (
                                  SELECT COALESCE(pp3.currentPrice, 0)
                                  FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp3
                                  WHERE pp3.productJpaEntity.id = :cursor
                                    AND pp3.id = (
                                        SELECT MAX(pp4.id)
                                        FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp4
                                        WHERE pp4.productJpaEntity.id = :cursor
                                    )
                              )
                           OR (COALESCE(pp.currentPrice, 0) = (
                                  SELECT COALESCE(pp3.currentPrice, 0)
                                  FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp3
                                  WHERE pp3.productJpaEntity.id = :cursor
                                    AND pp3.id = (
                                        SELECT MAX(pp4.id)
                                        FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp4
                                        WHERE pp4.productJpaEntity.id = :cursor
                                    )
                              ) AND p.id > :cursor)
                        ))
                     OR (:sortProperty = 'accumulatedPoint' AND (
                              COALESCE(pp.accumulatedPoint, 0) > (
                                  SELECT COALESCE(pp5.accumulatedPoint, 0)
                                  FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp5
                                  WHERE pp5.productJpaEntity.id = :cursor
                                    AND pp5.id = (
                                        SELECT MAX(pp6.id)
                                        FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp6
                                        WHERE pp6.productJpaEntity.id = :cursor
                                    )
                              )
                           OR (COALESCE(pp.accumulatedPoint, 0) = (
                                  SELECT COALESCE(pp5.accumulatedPoint, 0)
                                  FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp5
                                  WHERE pp5.productJpaEntity.id = :cursor
                                    AND pp5.id = (
                                        SELECT MAX(pp6.id)
                                        FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp6
                                        WHERE pp6.productJpaEntity.id = :cursor
                                    )
                              ) AND p.id > :cursor)
                        ))
                 )
              )
            ORDER BY
                CASE WHEN :sortProperty = 'orderNum' THEN p.orderNum END ASC,
                CASE WHEN :sortProperty = 'popularity' THEN p.sales END ASC,
                CASE WHEN :sortProperty = 'currentPrice' THEN COALESCE(pp.currentPrice, 0) END ASC,
                CASE WHEN :sortProperty = 'accumulatedPoint' THEN COALESCE(pp.accumulatedPoint, 0) END ASC,
                p.id ASC
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
              LEFT JOIN com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp
                ON pp.productJpaEntity = p
               AND pp.id = (
                    SELECT MAX(pp2.id)
                    FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp2
                    WHERE pp2.productJpaEntity = p
               )
            WHERE 1 = 1
              AND p.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
              AND (
                    :pattern IS NULL
                 OR (:searchTarget = 'name' AND p.name LIKE :pattern)
                 OR (:searchTarget = 'brandName' AND p.brandName LIKE :pattern)
                 OR (:searchTarget <> 'name' AND :searchTarget <> 'brandName' AND (p.name LIKE :pattern OR p.brandName LIKE :pattern))
              )
              AND (
                    :cursor IS NULL
                 OR NOT EXISTS (SELECT 1 FROM ProductJpaEntity p0 WHERE p0.id = :cursor)
                 OR (
                        (:sortProperty = 'orderNum' AND (
                              p.orderNum < (SELECT p2.orderNum FROM ProductJpaEntity p2 WHERE p2.id = :cursor)
                           OR (p.orderNum = (SELECT p2.orderNum FROM ProductJpaEntity p2 WHERE p2.id = :cursor) AND p.id < :cursor)
                        ))
                     OR (:sortProperty = 'popularity' AND (
                              p.sales < (SELECT p2.sales FROM ProductJpaEntity p2 WHERE p2.id = :cursor)
                           OR (p.sales = (SELECT p2.sales FROM ProductJpaEntity p2 WHERE p2.id = :cursor) AND p.id < :cursor)
                        ))
                     OR (:sortProperty = 'currentPrice' AND (
                              COALESCE(pp.currentPrice, 0) < (
                                  SELECT COALESCE(pp3.currentPrice, 0)
                                  FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp3
                                  WHERE pp3.productJpaEntity.id = :cursor
                                    AND pp3.id = (
                                        SELECT MAX(pp4.id)
                                        FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp4
                                        WHERE pp4.productJpaEntity.id = :cursor
                                    )
                              )
                           OR (COALESCE(pp.currentPrice, 0) = (
                                  SELECT COALESCE(pp3.currentPrice, 0)
                                  FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp3
                                  WHERE pp3.productJpaEntity.id = :cursor
                                    AND pp3.id = (
                                        SELECT MAX(pp4.id)
                                        FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp4
                                        WHERE pp4.productJpaEntity.id = :cursor
                                    )
                              ) AND p.id < :cursor)
                        ))
                     OR (:sortProperty = 'accumulatedPoint' AND (
                              COALESCE(pp.accumulatedPoint, 0) < (
                                  SELECT COALESCE(pp5.accumulatedPoint, 0)
                                  FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp5
                                  WHERE pp5.productJpaEntity.id = :cursor
                                    AND pp5.id = (
                                        SELECT MAX(pp6.id)
                                        FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp6
                                        WHERE pp6.productJpaEntity.id = :cursor
                                    )
                              )
                           OR (COALESCE(pp.accumulatedPoint, 0) = (
                                  SELECT COALESCE(pp5.accumulatedPoint, 0)
                                  FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp5
                                  WHERE pp5.productJpaEntity.id = :cursor
                                    AND pp5.id = (
                                        SELECT MAX(pp6.id)
                                        FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp6
                                        WHERE pp6.productJpaEntity.id = :cursor
                                    )
                              ) AND p.id < :cursor)
                        ))
                 )
              )
            ORDER BY
                CASE WHEN :sortProperty = 'orderNum' THEN p.orderNum END DESC,
                CASE WHEN :sortProperty = 'popularity' THEN p.sales END DESC,
                CASE WHEN :sortProperty = 'currentPrice' THEN COALESCE(pp.currentPrice, 0) END DESC,
                CASE WHEN :sortProperty = 'accumulatedPoint' THEN COALESCE(pp.accumulatedPoint, 0) END DESC,
                p.id DESC
            """)
    List<ProductJpaEntity> findAllActiveByCursorDesc(
            @Param("cursor") Long cursor,
            @Param("pageable") Pageable pageable,
            @Param("sortProperty") String sortProperty,
            @Param("searchTarget") String searchTarget,
            @Param("pattern") String pattern);

    @Query("""
            SELECT p
            FROM ProductJpaEntity p
              LEFT JOIN com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp
                ON pp.productJpaEntity = p
               AND pp.id = (
                    SELECT MAX(pp2.id)
                    FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp2
                    WHERE pp2.productJpaEntity = p
               )
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
                    :cursor IS NULL
                 OR NOT EXISTS (SELECT 1 FROM ProductJpaEntity p0 WHERE p0.id = :cursor)
                 OR (
                        (:sortProperty = 'orderNum' AND (
                              p.orderNum > (SELECT p2.orderNum FROM ProductJpaEntity p2 WHERE p2.id = :cursor)
                           OR (p.orderNum = (SELECT p2.orderNum FROM ProductJpaEntity p2 WHERE p2.id = :cursor) AND p.id > :cursor)
                        ))
                     OR (:sortProperty = 'popularity' AND (
                              p.sales > (SELECT p2.sales FROM ProductJpaEntity p2 WHERE p2.id = :cursor)
                           OR (p.sales = (SELECT p2.sales FROM ProductJpaEntity p2 WHERE p2.id = :cursor) AND p.id > :cursor)
                        ))
                     OR (:sortProperty = 'currentPrice' AND (
                              COALESCE(pp.currentPrice, 0) > (
                                  SELECT COALESCE(pp3.currentPrice, 0)
                                  FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp3
                                  WHERE pp3.productJpaEntity.id = :cursor
                                    AND pp3.id = (
                                        SELECT MAX(pp4.id)
                                        FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp4
                                        WHERE pp4.productJpaEntity.id = :cursor
                                    )
                              )
                           OR (COALESCE(pp.currentPrice, 0) = (
                                  SELECT COALESCE(pp3.currentPrice, 0)
                                  FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp3
                                  WHERE pp3.productJpaEntity.id = :cursor
                                    AND pp3.id = (
                                        SELECT MAX(pp4.id)
                                        FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp4
                                        WHERE pp4.productJpaEntity.id = :cursor
                                    )
                              ) AND p.id > :cursor)
                        ))
                     OR (:sortProperty = 'accumulatedPoint' AND (
                              COALESCE(pp.accumulatedPoint, 0) > (
                                  SELECT COALESCE(pp5.accumulatedPoint, 0)
                                  FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp5
                                  WHERE pp5.productJpaEntity.id = :cursor
                                    AND pp5.id = (
                                        SELECT MAX(pp6.id)
                                        FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp6
                                        WHERE pp6.productJpaEntity.id = :cursor
                                    )
                              )
                           OR (COALESCE(pp.accumulatedPoint, 0) = (
                                  SELECT COALESCE(pp5.accumulatedPoint, 0)
                                  FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp5
                                  WHERE pp5.productJpaEntity.id = :cursor
                                    AND pp5.id = (
                                        SELECT MAX(pp6.id)
                                        FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp6
                                        WHERE pp6.productJpaEntity.id = :cursor
                                    )
                              ) AND p.id > :cursor)
                        ))
                 )
              )
            ORDER BY
                CASE WHEN :sortProperty = 'orderNum' THEN p.orderNum END ASC,
                CASE WHEN :sortProperty = 'popularity' THEN p.sales END ASC,
                CASE WHEN :sortProperty = 'currentPrice' THEN COALESCE(pp.currentPrice, 0) END ASC,
                CASE WHEN :sortProperty = 'accumulatedPoint' THEN COALESCE(pp.accumulatedPoint, 0) END ASC,
                p.id ASC
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
              LEFT JOIN com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp
                ON pp.productJpaEntity = p
               AND pp.id = (
                    SELECT MAX(pp2.id)
                    FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp2
                    WHERE pp2.productJpaEntity = p
               )
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
                    :cursor IS NULL
                 OR NOT EXISTS (SELECT 1 FROM ProductJpaEntity p0 WHERE p0.id = :cursor)
                 OR (
                        (:sortProperty = 'orderNum' AND (
                              p.orderNum < (SELECT p2.orderNum FROM ProductJpaEntity p2 WHERE p2.id = :cursor)
                           OR (p.orderNum = (SELECT p2.orderNum FROM ProductJpaEntity p2 WHERE p2.id = :cursor) AND p.id < :cursor)
                        ))
                     OR (:sortProperty = 'popularity' AND (
                              p.sales < (SELECT p2.sales FROM ProductJpaEntity p2 WHERE p2.id = :cursor)
                           OR (p.sales = (SELECT p2.sales FROM ProductJpaEntity p2 WHERE p2.id = :cursor) AND p.id < :cursor)
                        ))
                     OR (:sortProperty = 'currentPrice' AND (
                              COALESCE(pp.currentPrice, 0) < (
                                  SELECT COALESCE(pp3.currentPrice, 0)
                                  FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp3
                                  WHERE pp3.productJpaEntity.id = :cursor
                                    AND pp3.id = (
                                        SELECT MAX(pp4.id)
                                        FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp4
                                        WHERE pp4.productJpaEntity.id = :cursor
                                    )
                              )
                           OR (COALESCE(pp.currentPrice, 0) = (
                                  SELECT COALESCE(pp3.currentPrice, 0)
                                  FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp3
                                  WHERE pp3.productJpaEntity.id = :cursor
                                    AND pp3.id = (
                                        SELECT MAX(pp4.id)
                                        FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp4
                                        WHERE pp4.productJpaEntity.id = :cursor
                                    )
                              ) AND p.id < :cursor)
                        ))
                     OR (:sortProperty = 'accumulatedPoint' AND (
                              COALESCE(pp.accumulatedPoint, 0) < (
                                  SELECT COALESCE(pp5.accumulatedPoint, 0)
                                  FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp5
                                  WHERE pp5.productJpaEntity.id = :cursor
                                    AND pp5.id = (
                                        SELECT MAX(pp6.id)
                                        FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp6
                                        WHERE pp6.productJpaEntity.id = :cursor
                                    )
                              )
                           OR (COALESCE(pp.accumulatedPoint, 0) = (
                                  SELECT COALESCE(pp5.accumulatedPoint, 0)
                                  FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp5
                                  WHERE pp5.productJpaEntity.id = :cursor
                                    AND pp5.id = (
                                        SELECT MAX(pp6.id)
                                        FROM com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity pp6
                                        WHERE pp6.productJpaEntity.id = :cursor
                                    )
                              ) AND p.id < :cursor)
                        ))
                 )
              )
            ORDER BY
                CASE WHEN :sortProperty = 'orderNum' THEN p.orderNum END DESC,
                CASE WHEN :sortProperty = 'popularity' THEN p.sales END DESC,
                CASE WHEN :sortProperty = 'currentPrice' THEN COALESCE(pp.currentPrice, 0) END DESC,
                CASE WHEN :sortProperty = 'accumulatedPoint' THEN COALESCE(pp.accumulatedPoint, 0) END DESC,
                p.id DESC
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
            @Param("pattern") String pattern);
}
