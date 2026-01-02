package com.personal.marketnote.product.adapter.out.persistence.productoption.repository;

import com.personal.marketnote.product.adapter.out.persistence.productoption.entity.ProductOptionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOptionJpaRepository extends JpaRepository<ProductOptionJpaEntity, Long> {
    @Query("""
            select o.id
            from com.personal.marketnote.product.adapter.out.persistence.productoption.entity.ProductOptionJpaEntity o
            where o.productOptionCategoryJpaEntity.id = :categoryId
            """)
    List<Long> findIdsByCategoryId(@Param("categoryId") Long categoryId);

    @Modifying
    @Query("""
            delete
            from com.personal.marketnote.product.adapter.out.persistence.productoption.entity.ProductOptionJpaEntity o
            where o.productOptionCategoryJpaEntity.id = :categoryId
            """)
    void deleteAllByCategoryId(@Param("categoryId") Long categoryId);
}
