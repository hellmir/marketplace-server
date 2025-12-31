package com.personal.marketnote.product.adapter.in.client.product.mapper;

import com.personal.marketnote.product.adapter.in.client.product.request.RegisterProductCategoriesRequest;
import com.personal.marketnote.product.adapter.in.client.product.request.RegisterProductRequest;
import com.personal.marketnote.product.port.in.command.RegisterProductCategoriesCommand;
import com.personal.marketnote.product.port.in.command.RegisterProductCommand;

public class ProductRequestToCommandMapper {
    public static RegisterProductCommand mapToCommand(RegisterProductRequest registerProductRequest) {
        return RegisterProductCommand.of(
                registerProductRequest.getSellerId(),
                registerProductRequest.getName(),
                registerProductRequest.getDetail()
        );
    }

    public static RegisterProductCategoriesCommand mapToCommand(Long productId, RegisterProductCategoriesRequest registerProductCategoriesRequest) {
        return RegisterProductCategoriesCommand.of(
                productId,
                registerProductCategoriesRequest.getCategoryIds()
        );
    }
}
