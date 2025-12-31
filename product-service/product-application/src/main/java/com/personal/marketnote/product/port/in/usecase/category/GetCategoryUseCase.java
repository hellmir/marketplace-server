package com.personal.marketnote.product.port.in.usecase.category;

import com.personal.marketnote.product.domain.category.Category;
import com.personal.marketnote.product.port.in.result.GetCategoriesResult;

public interface GetCategoryUseCase {
    /**
     * @param id 카테고리 ID
     * @return 카테고리 도메인 {@link Category}
     * @Date 2025-12-31
     * @Author 성효빈
     * @Description 카테고리를 조회합니다.
     */
    Category getCategory(Long id);

    /**
     * @param parentCategoryId 상위 카테고리 ID
     * @return 카테고리 목록 {@link GetCategoriesResult}
     * @Date 2025-12-31
     * @Author 성효빈
     * @Description 상위 카테고리 ID에 해당하는 카테고리 목록을 조회합니다.
     */
    GetCategoriesResult getCategoriesByParentId(Long parentCategoryId);
}


