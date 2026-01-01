package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.port.in.command.RegisterProductOptionsCommand;
import com.personal.marketnote.product.port.in.result.RegisterProductOptionsResult;

public interface RegisterProductOptionsUseCase {
    RegisterProductOptionsResult registerProductOptions(
            Long userId, boolean isAdmin, RegisterProductOptionsCommand command
    );
}


