package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.port.in.command.UpdateProductOptionsCommand;
import com.personal.marketnote.product.port.in.result.UpsertProductOptionsResult;

public interface UpdateProductOptionsUseCase {
    UpsertProductOptionsResult updateProductOptions(
            Long userId, boolean isAdmin, UpdateProductOptionsCommand command
    );
}
