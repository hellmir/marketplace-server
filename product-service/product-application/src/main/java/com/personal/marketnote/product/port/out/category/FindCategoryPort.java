package com.personal.marketnote.product.port.out.category;

import com.personal.marketnote.product.domain.category.Category;

import java.util.List;
import java.util.Optional;

public interface FindCategoryPort {
    Optional<Category> findById(Long id);

    List<Category> findActiveByParentId(Long parentCategoryId);

    List<Category> findAllActiveByIds(List<Long> categoryIds);

    boolean existsById(Long categoryId);

    boolean existsChildren(Long categoryId);
}


