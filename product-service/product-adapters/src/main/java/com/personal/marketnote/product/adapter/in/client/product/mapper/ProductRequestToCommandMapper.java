package com.personal.marketnote.product.adapter.in.client.product.mapper;

import com.personal.marketnote.product.adapter.in.client.product.request.RegisterProductCategoriesRequest;
import com.personal.marketnote.product.adapter.in.client.product.request.RegisterProductRequest;
import com.personal.marketnote.product.adapter.in.client.product.request.UpsertProductOptionsRequest;
import com.personal.marketnote.product.port.in.command.RegisterProductCategoriesCommand;
import com.personal.marketnote.product.port.in.command.RegisterProductCommand;
import com.personal.marketnote.product.port.in.command.RegisterProductOptionsCommand;
import com.personal.marketnote.product.port.in.command.UpdateProductCommand;

public class ProductRequestToCommandMapper {
    public static RegisterProductCommand mapToCommand(RegisterProductRequest registerProductRequest) {
        return RegisterProductCommand.of(
                registerProductRequest.getSellerId(),
                registerProductRequest.getName(),
                registerProductRequest.getBrandName(),
                registerProductRequest.getDetail(),
                registerProductRequest.getPrice(),
                registerProductRequest.getAccumulatedPoint());
    }

    public static RegisterProductCategoriesCommand mapToCommand(
            Long productId, RegisterProductCategoriesRequest registerProductCategoriesRequest) {
        return RegisterProductCategoriesCommand.of(
                productId,
                registerProductCategoriesRequest.getCategoryIds());
    }

    public static RegisterProductOptionsCommand mapToCommand(
            Long productId, UpsertProductOptionsRequest request) {
        java.util.List<RegisterProductOptionsCommand.OptionItem> optionItems = request.getOptions().stream()
                .map(o -> new RegisterProductOptionsCommand.OptionItem(
                        o.getContent(), o.getPrice(), o.getAccumulatedPoint()))
                .toList();
        return RegisterProductOptionsCommand.of(productId, request.getCategoryName(), optionItems);
    }

    public static com.personal.marketnote.product.port.in.command.UpdateProductOptionsCommand mapToUpdateCommand(
            Long productId, Long optionCategoryId, UpsertProductOptionsRequest request) {
        java.util.List<RegisterProductOptionsCommand.OptionItem> optionItems = request.getOptions().stream()
                .map(o -> new RegisterProductOptionsCommand.OptionItem(
                        o.getContent(), o.getPrice(), o.getAccumulatedPoint()))
                .toList();
        return com.personal.marketnote.product.port.in.command.UpdateProductOptionsCommand.of(
                productId, optionCategoryId, request.getCategoryName(), optionItems);
    }

    public static UpdateProductCommand mapToCommand(
            Long productId, com.personal.marketnote.product.adapter.in.client.product.request.UpdateProductRequest request
    ) {
        return UpdateProductCommand.of(
                productId,
                request.getName(),
                request.getBrandName(),
                request.getDetail()
        );
    }
}
