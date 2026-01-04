package com.personal.marketnote.product.adapter.out.mapper;

import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductTagJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.productoption.entity.ProductOptionCategoryJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.productoption.entity.ProductOptionJpaEntity;
import com.personal.marketnote.product.domain.option.ProductOption;
import com.personal.marketnote.product.domain.option.ProductOptionCategory;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.domain.product.ProductTag;

import java.util.List;
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
                                productJpaEntity.getPrice(),
                                productJpaEntity.getDiscountPrice(),
                                productJpaEntity.getDiscountRate(),
                                productJpaEntity.getAccumulatedPoint(),
                                productJpaEntity.getSales(),
                                productJpaEntity.getViewCount(),
                                productJpaEntity.getPopularity(),
                                productJpaEntity.isFindAllOptionsYn(),
                                productJpaEntity.getProductTagJpaEntities().stream()
                                        .map(ProductJpaEntityToDomainMapper::mapToDomain)
                                        .filter(Optional::isPresent)
                                        .map(Optional::get)
                                        .toList(),
                                productJpaEntity.getOrderNum(),
                                productJpaEntity.getStatus()
                        )
                );
    }

    public static Optional<ProductOptionCategory> mapToDomain(
            ProductOptionCategoryJpaEntity productOptionCategoryJpaEntity
    ) {
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

                    List<ProductOption> options = entity.getProductOptionJpaEntities()
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

    public static Optional<ProductOption> mapToDomain(ProductOptionJpaEntity productOptionJpaEntity) {
        return Optional.ofNullable(productOptionJpaEntity)
                .map(entity -> {
                    ProductOptionCategoryJpaEntity categoryEntity = entity
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
                            entity.getStatus()
                    );
                });
    }

    private static Optional<ProductOption> mapToDomain(
            ProductOptionJpaEntity productOptionJpaEntity,
            ProductOptionCategory shallowCategory) {
        return Optional.ofNullable(productOptionJpaEntity)
                .map(
                        entity -> ProductOption.of(
                                entity.getId(),
                                shallowCategory,
                                entity.getContent(),
                                entity.getStatus()
                        )
                );
    }

    private static Optional<ProductTag> mapToDomain(ProductTagJpaEntity productTagJpaEntity) {
        return Optional.ofNullable(productTagJpaEntity)
                .map(entity -> ProductTag.of(
                        entity.getId(),
                        entity.getProductJpaEntity().getId(),
                        entity.getName(),
                        entity.getOrderNum(),
                        entity.getStatus()
                ));
    }
}
