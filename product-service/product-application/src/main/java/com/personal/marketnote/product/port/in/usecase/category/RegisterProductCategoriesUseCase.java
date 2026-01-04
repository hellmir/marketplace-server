package com.personal.marketnote.product.port.in.usecase.category;

import com.personal.marketnote.product.port.in.command.RegisterProductCategoriesCommand;
import com.personal.marketnote.product.port.in.result.category.RegisterProductCategoriesResult;

public interface RegisterProductCategoriesUseCase {
    RegisterProductCategoriesResult registerProductCategories(
            Long userId, boolean isAdmin, RegisterProductCategoriesCommand command
    );
}
