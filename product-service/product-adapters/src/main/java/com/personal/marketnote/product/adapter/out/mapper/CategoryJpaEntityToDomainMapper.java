package com.personal.marketnote.product.adapter.out.mapper;

import com.personal.marketnote.product.adapter.out.persistence.category.entity.CategoryJpaEntity;
import com.personal.marketnote.product.domain.category.Category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryJpaEntityToDomainMapper {
    public static Optional<Category> mapToDomain(CategoryJpaEntity entity) {
        return Optional.ofNullable(entity)
                .map(e -> Category.of(
                        e.getId(),
                        e.getParentCategoryId(),
                        e.getName(),
                        e.getStatus()
                ));
    }

    public static List<Category> mapToDomainList(List<CategoryJpaEntity> entities) {
        return entities.stream()
                .map(CategoryJpaEntityToDomainMapper::mapToDomain)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}


