package com.personal.marketnote.product.adapter.in.client.product.mapper;

import com.personal.marketnote.product.adapter.in.client.product.request.RegisterProductCategoriesRequest;
import com.personal.marketnote.product.adapter.in.client.product.request.RegisterProductRequest;
import com.personal.marketnote.product.adapter.in.client.product.request.UpdateProductOptionsRequest;
import com.personal.marketnote.product.adapter.in.client.product.request.UpdateProductRequest;
import com.personal.marketnote.product.port.in.command.*;

import java.util.List;

public class ProductRequestToCommandMapper {
    public static RegisterProductCommand mapToCommand(RegisterProductRequest registerProductRequest) {
        return RegisterProductCommand.of(
                registerProductRequest.getSellerId(),
                registerProductRequest.getName(),
                registerProductRequest.getBrandName(),
                registerProductRequest.getDetail(),
                registerProductRequest.getPrice(),
                registerProductRequest.getDiscountPrice(),
                registerProductRequest.getAccumulatedPoint(),
                registerProductRequest.getIsFindAllOptions(),
                registerProductRequest.getTags()
        );
    }

    public static RegisterProductCategoriesCommand mapToCommand(
            Long productId, RegisterProductCategoriesRequest registerProductCategoriesRequest) {
        return RegisterProductCategoriesCommand.of(
                productId,
                registerProductCategoriesRequest.getCategoryIds());
    }

    public static RegisterProductOptionsCommand mapToCommand(
            Long productId, UpdateProductOptionsRequest request) {
        List<RegisterProductOptionsCommand.OptionItem> optionItems = request.getOptions().stream()
                .map(o -> new RegisterProductOptionsCommand.OptionItem(o.getContent()))
                .toList();
        return RegisterProductOptionsCommand.of(productId, request.getCategoryName(), optionItems);
    }

    public static UpdateProductOptionsCommand mapToUpdateCommand(
            Long productId, Long optionCategoryId, UpdateProductOptionsRequest request) {
        List<RegisterProductOptionsCommand.OptionItem> optionItems = request.getOptions().stream()
                .map(o -> new RegisterProductOptionsCommand.OptionItem(o.getContent()))
                .toList();
        return UpdateProductOptionsCommand.of(
                productId, optionCategoryId, request.getCategoryName(), optionItems
        );
    }

    public static UpdateProductCommand mapToCommand(
            Long id, UpdateProductRequest request
    ) {
        return UpdateProductCommand.of(
                id,
                request.getName(),
                request.getBrandName(),
                request.getDetail(),
                request.getIsFindAllOptions(),
                request.getTags()
        );
    }
}
