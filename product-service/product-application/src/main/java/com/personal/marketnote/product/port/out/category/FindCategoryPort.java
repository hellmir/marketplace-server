package com.personal.marketnote.product.port.out.category;

import com.personal.marketnote.product.domain.category.Category;

import java.util.List;

public interface FindCategoryPort {
    List<Category> findActiveByParentId(Long parentCategoryId);

    List<Category> findAllActiveByIds(List<Long> categoryIds);
}


