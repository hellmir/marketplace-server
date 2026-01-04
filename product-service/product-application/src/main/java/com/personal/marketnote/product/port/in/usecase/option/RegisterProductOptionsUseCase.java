package com.personal.marketnote.product.port.in.usecase.option;

import com.personal.marketnote.product.port.in.command.RegisterProductOptionsCommand;
import com.personal.marketnote.product.port.in.result.option.UpdateProductOptionsResult;

public interface RegisterProductOptionsUseCase {
    UpdateProductOptionsResult registerProductOptions(
            Long userId, boolean isAdmin, RegisterProductOptionsCommand command
    );
}
