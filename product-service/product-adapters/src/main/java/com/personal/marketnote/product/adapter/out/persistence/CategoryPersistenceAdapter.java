package com.personal.marketnote.product.adapter.out.persistence;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.product.adapter.out.mapper.CategoryJpaEntityToDomainMapper;
import com.personal.marketnote.product.adapter.out.persistence.category.entity.CategoryJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.category.repository.CategoryJpaRepository;
import com.personal.marketnote.product.domain.category.Category;
import com.personal.marketnote.product.port.out.category.FindCategoryPort;
import com.personal.marketnote.product.port.out.category.SaveCategoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements FindCategoryPort, SaveCategoryPort {
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public List<Category> findActiveByParentId(Long parentCategoryId) {
        return CategoryJpaEntityToDomainMapper.mapToDomainList(
                categoryJpaRepository.findActiveByParentId(parentCategoryId)
        );
    }

    @Override
    public List<Category> findAllActiveByIds(List<Long> categoryIds) {
        return CategoryJpaEntityToDomainMapper.mapToDomainList(
                categoryJpaRepository.findAllByIdInAndStatus(categoryIds, EntityStatus.ACTIVE)
        );
    }

    @Override
    public Category save(Category category) {
        CategoryJpaEntity entity = CategoryJpaEntity.of(
                category.getParentCategoryId(),
                category.getName()
        );
        CategoryJpaEntity saved = categoryJpaRepository.save(entity);
        return CategoryJpaEntityToDomainMapper.mapToDomain(saved).get();
    }
}


