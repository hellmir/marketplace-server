package com.personal.marketnote.product.adapter.out.mapper;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductJpaEntity;
import com.personal.marketnote.product.domain.product.Product;

import java.util.Optional;

public final class ProductJpaEntityToDomainMapper {
    private ProductJpaEntityToDomainMapper() {
    }

    public static Optional<Product> mapToDomain(ProductJpaEntity entity) {
        if (entity == null) {
            return Optional.empty();
        }

        return Optional.of(
                Product.of(
                        entity.getId(),
                        entity.getSellerId(),
                        entity.getName(),
                        entity.getDetail(),
                        entity.getSales(),
                        entity.getOrderNumber(),
                        EntityStatus.ACTIVE // BaseGeneralEntity keeps status internally; default ACTIVE
                )
        );
    }
}


