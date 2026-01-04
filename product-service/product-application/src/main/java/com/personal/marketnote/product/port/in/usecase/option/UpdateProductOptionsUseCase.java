package com.personal.marketnote.product.port.in.usecase.option;

import com.personal.marketnote.product.port.in.command.UpdateProductOptionsCommand;
import com.personal.marketnote.product.port.in.result.option.UpdateProductOptionsResult;

public interface UpdateProductOptionsUseCase {
    UpdateProductOptionsResult updateProductOptions(
            Long userId, boolean isAdmin, UpdateProductOptionsCommand command
    );
}
