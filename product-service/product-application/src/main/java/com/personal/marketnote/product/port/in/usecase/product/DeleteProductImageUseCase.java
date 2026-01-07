package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.port.in.command.DeleteProductImageCommand;

public interface DeleteProductImageUseCase {
    void delete(Long userId, boolean isAdmin, DeleteProductImageCommand command);
}
