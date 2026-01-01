package com.personal.marketnote.product.adapter.out.persistence.productoption.repository;

import com.personal.marketnote.product.adapter.out.persistence.productoption.entity.ProductOptionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionJpaRepository extends JpaRepository<ProductOptionJpaEntity, Long> {
}
