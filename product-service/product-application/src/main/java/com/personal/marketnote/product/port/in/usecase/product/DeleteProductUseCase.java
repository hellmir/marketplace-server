package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.port.in.command.DeleteProductCommand;

public interface DeleteProductUseCase {
    void delete(Long userId, boolean isAdmin, DeleteProductCommand command);
}
