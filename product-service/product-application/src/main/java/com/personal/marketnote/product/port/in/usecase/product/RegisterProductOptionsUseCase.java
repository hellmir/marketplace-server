package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.port.in.command.RegisterProductOptionsCommand;
import com.personal.marketnote.product.port.in.result.UpsertProductOptionsResult;

public interface RegisterProductOptionsUseCase {
    UpsertProductOptionsResult registerProductOptions(
            Long userId, boolean isAdmin, RegisterProductOptionsCommand command
    );
}
