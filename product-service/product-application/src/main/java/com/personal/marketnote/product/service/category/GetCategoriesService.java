package com.personal.marketnote.product.service.category;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.domain.category.Category;
import com.personal.marketnote.product.port.in.result.GetCategoriesResult;
import com.personal.marketnote.product.port.in.usecase.category.GetCategoriesUseCase;
import com.personal.marketnote.product.port.out.category.FindCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_UNCOMMITTED, readOnly = true)
public class GetCategoriesService implements GetCategoriesUseCase {
    private final FindCategoryPort findCategoryPort;

    @Override
    public GetCategoriesResult getCategoriesByParentId(Long parentCategoryId) {
        List<Category> categories = findCategoryPort.findActiveByParentId(parentCategoryId);
        return GetCategoriesResult.from(categories);
    }
}


