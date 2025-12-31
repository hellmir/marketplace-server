package com.personal.marketnote.product.port.in.usecase.category;

import com.personal.marketnote.product.port.in.command.RegisterCategoryCommand;
import com.personal.marketnote.product.port.in.result.RegisterCategoryResult;

public interface RegisterCategoryUseCase {
    RegisterCategoryResult registerCategory(RegisterCategoryCommand command);
}


