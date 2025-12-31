package com.personal.marketnote.product.port.in.usecase.category;

import com.personal.marketnote.product.port.in.result.GetCategoriesResult;

public interface GetCategoriesUseCase {
    GetCategoriesResult getCategoriesByParentId(Long parentCategoryId);
}


