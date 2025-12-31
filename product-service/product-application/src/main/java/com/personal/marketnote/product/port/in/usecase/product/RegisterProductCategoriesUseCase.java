package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.port.in.command.RegisterProductCategoriesCommand;
import com.personal.marketnote.product.port.in.result.RegisterProductCategoriesResult;

public interface RegisterProductCategoriesUseCase {
    RegisterProductCategoriesResult registerProductCategories(
            Long userId, boolean isAdmin, RegisterProductCategoriesCommand command
    );
}
