package com.personal.marketnote.product.adapter.in.client.product.mapper;

import com.personal.marketnote.product.adapter.in.client.category.request.RegisterCategoryRequest;
import com.personal.marketnote.product.port.in.command.RegisterCategoryCommand;

public class CategoryRequestToCommandMapper {
    public static RegisterCategoryCommand mapToCommand(RegisterCategoryRequest registerCategoryRequest) {
        return RegisterCategoryCommand.of(
                registerCategoryRequest.getParentCategoryId(),
                registerCategoryRequest.getName()
        );
    }
}
