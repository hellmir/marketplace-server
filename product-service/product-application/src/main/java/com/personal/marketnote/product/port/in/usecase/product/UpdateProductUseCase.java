package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.port.in.command.UpdateProductCommand;

public interface UpdateProductUseCase {
    void update(Long userId, boolean isAdmin, UpdateProductCommand command);
}


