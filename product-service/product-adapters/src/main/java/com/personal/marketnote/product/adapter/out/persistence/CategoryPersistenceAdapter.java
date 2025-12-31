package com.personal.marketnote.product.adapter.out.persistence;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.product.adapter.out.mapper.CategoryJpaEntityToDomainMapper;
import com.personal.marketnote.product.adapter.out.persistence.category.repository.CategoryJpaRepository;
import com.personal.marketnote.product.domain.category.Category;
import com.personal.marketnote.product.port.out.category.FindCategoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements FindCategoryPort {
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
}


