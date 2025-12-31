package com.personal.marketnote.product.adapter.out.persistence.product.repository;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {
    boolean existsByIdAndSellerId(Long productId, Long sellerId);

    List<ProductJpaEntity> findAllByStatusOrderByOrderNumAsc(EntityStatus status);

    @Query("""
            SELECT p
            FROM ProductJpaEntity p
            WHERE 1 = 1
              AND p.status = com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE
              AND EXISTS (
                SELECT 1
                FROM com.personal.marketnote.product.adapter.out.persistence.productcategory.entity.ProductCategoryJpaEntity pc
                WHERE pc.productId = p.id
                  AND pc.categoryId = :categoryId
              )
            ORDER BY p.orderNum ASC
            """)
    List<ProductJpaEntity> findAllActiveByCategoryId(@Param("categoryId") Long categoryId);
}
