package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.port.in.command.RegisterProductCommand;
import com.personal.marketnote.product.port.in.result.RegisterProductResult;

public interface RegisterProductUseCase {
    RegisterProductResult registerProduct(RegisterProductCommand command);
}
