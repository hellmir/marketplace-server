package com.personal.marketnote.product.adapter.out.mapper;

import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.productoption.entity.ProductOptionCategoryJpaGeneralEntity;
import com.personal.marketnote.product.adapter.out.persistence.productoption.entity.ProductOptionJpaGeneralEntity;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.domain.product.ProductOption;
import com.personal.marketnote.product.domain.product.ProductOptionCategory;

import java.util.Optional;

public class ProductJpaEntityToDomainMapper {
    public static Optional<Product> mapToDomain(ProductJpaEntity productJpaEntity) {
        return Optional.ofNullable(productJpaEntity)
                .map(
                        entity -> Product.of(
                                productJpaEntity.getId(),
                                productJpaEntity.getSellerId(),
                                productJpaEntity.getName(),
                                productJpaEntity.getBrandName(),
                                productJpaEntity.getDetail(),
                                productJpaEntity.getCurrentPrice(),
                                productJpaEntity.getAccumulatedPoint(),
                                productJpaEntity.getSales(),
                                productJpaEntity.getViewCount(),
                                productJpaEntity.getPopularity(),
                                productJpaEntity.getOrderNum(),
                                productJpaEntity.getStatus()
                        )
                );
    }

    public static Optional<ProductOptionCategory> mapToDomain(
            ProductOptionCategoryJpaGeneralEntity productOptionCategoryJpaEntity) {
        return Optional.ofNullable(productOptionCategoryJpaEntity)
                .map(entity -> {
                    Product product = ProductJpaEntityToDomainMapper
                            .mapToDomain(entity.getProductJpaEntity()).orElse(null);
                    ProductOptionCategory shallowCategory = ProductOptionCategory.of(
                            entity.getId(),
                            product,
                            entity.getName(),
                            null,
                            entity.getOrderNum(),
                            entity.getStatus()
                    );

                    java.util.List<ProductOption> options = entity.getProductOptionJpaEntities()
                            .stream()
                            .map(optEntity -> mapToDomain(optEntity, shallowCategory))
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .toList();

                    return ProductOptionCategory.of(
                            entity.getId(),
                            product,
                            entity.getName(),
                            options,
                            entity.getOrderNum(),
                            entity.getStatus()
                    );
                });
    }

    public static Optional<ProductOption> mapToDomain(ProductOptionJpaGeneralEntity productOptionJpaEntity) {
        return Optional.ofNullable(productOptionJpaEntity)
                .map(entity -> {
                    ProductOptionCategoryJpaGeneralEntity categoryEntity = entity
                            .getProductOptionCategoryJpaEntity();
                    Product product = FormatValidator.hasValue(categoryEntity)
                            ? ProductJpaEntityToDomainMapper
                            .mapToDomain(categoryEntity.getProductJpaEntity())
                            .orElse(null)
                            : null;
                    ProductOptionCategory shallowCategory = !FormatValidator.hasValue(categoryEntity)
                            ? null
                            : ProductOptionCategory.of(
                            categoryEntity.getId(),
                            product,
                            categoryEntity.getName(),
                            null,
                            categoryEntity.getOrderNum(),
                            categoryEntity.getStatus()
                    );

                    return ProductOption.of(
                            entity.getId(),
                            shallowCategory,
                            entity.getContent(),
                            entity.getPrice(),
                            entity.getAccumulatedPoint(),
                            entity.getStatus()
                    );
                });
    }

    private static Optional<ProductOption> mapToDomain(
            ProductOptionJpaGeneralEntity productOptionJpaEntity,
            ProductOptionCategory shallowCategory) {
        return Optional.ofNullable(productOptionJpaEntity)
                .map(
                        entity -> ProductOption.of(
                                entity.getId(),
                                shallowCategory,
                                entity.getContent(),
                                entity.getPrice(),
                                entity.getAccumulatedPoint(),
                                entity.getStatus()
                        )
                );
    }
}
