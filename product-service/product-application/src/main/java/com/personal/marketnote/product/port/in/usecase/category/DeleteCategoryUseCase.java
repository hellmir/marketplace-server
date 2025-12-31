package com.personal.marketnote.product.port.in.usecase.category;

import com.personal.marketnote.product.port.in.command.DeleteCategoryCommand;

public interface DeleteCategoryUseCase {
    /**
     * @param command 삭제 카테고리 커맨드
     * @Date 2025-12-31
     * @Author 성효빈
     * @Description 카테고리를 삭제합니다.
     */
    void deleteCategory(DeleteCategoryCommand command);
}


