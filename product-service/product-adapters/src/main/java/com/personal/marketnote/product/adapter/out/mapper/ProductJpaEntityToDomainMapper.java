package com.personal.marketnote.product.adapter.out.mapper;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductJpaEntity;
import com.personal.marketnote.product.domain.product.Product;

import java.util.Optional;

public class ProductJpaEntityToDomainMapper {
    public static Optional<Product> mapToDomain(ProductJpaEntity productJpaEntity) {
        return Optional.ofNullable(productJpaEntity)
                .map(entity ->
                        Product.of(
                                productJpaEntity.getId(),
                                productJpaEntity.getSellerId(),
                                productJpaEntity.getName(),
                                productJpaEntity.getDetail(),
                                productJpaEntity.getSales(),
                                productJpaEntity.getOrderNum(),
                                EntityStatus.ACTIVE
                        )
                );
    }
}


