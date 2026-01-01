package com.personal.marketnote.product.adapter.in.client.product.mapper;

import com.personal.marketnote.product.adapter.in.client.product.request.RegisterProductCategoriesRequest;
import com.personal.marketnote.product.adapter.in.client.product.request.RegisterProductOptionsRequest;
import com.personal.marketnote.product.adapter.in.client.product.request.RegisterProductRequest;
import com.personal.marketnote.product.port.in.command.RegisterProductCategoriesCommand;
import com.personal.marketnote.product.port.in.command.RegisterProductCommand;
import com.personal.marketnote.product.port.in.command.RegisterProductOptionsCommand;

public class ProductRequestToCommandMapper {
    public static RegisterProductCommand mapToCommand(RegisterProductRequest registerProductRequest) {
        return RegisterProductCommand.of(
                registerProductRequest.getSellerId(),
                registerProductRequest.getName(),
                registerProductRequest.getBrandName(),
                registerProductRequest.getDetail());
    }

    public static RegisterProductCategoriesCommand mapToCommand(
            Long productId, RegisterProductCategoriesRequest registerProductCategoriesRequest) {
        return RegisterProductCategoriesCommand.of(
                productId,
                registerProductCategoriesRequest.getCategoryIds());
    }

    public static RegisterProductOptionsCommand mapToCommand(
            Long productId, RegisterProductOptionsRequest request) {
        java.util.List<RegisterProductOptionsCommand.OptionItem> optionItems = request.getOptions().stream()
                .map(o -> new RegisterProductOptionsCommand.OptionItem(
                        o.getContent(), o.getPrice(), o.getAccumulatedPoint()))
                .toList();
        return RegisterProductOptionsCommand.of(productId, request.getCategoryName(), optionItems);
    }
}
