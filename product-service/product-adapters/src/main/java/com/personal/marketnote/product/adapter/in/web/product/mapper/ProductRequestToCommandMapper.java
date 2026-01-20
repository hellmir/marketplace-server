package com.personal.marketnote.product.adapter.in.web.product.mapper;

import com.personal.marketnote.product.adapter.in.web.cart.request.GetMyOrderingProductsRequest;
import com.personal.marketnote.product.adapter.in.web.category.request.RegisterProductCategoriesRequest;
import com.personal.marketnote.product.adapter.in.web.option.request.UpdateProductOptionsRequest;
import com.personal.marketnote.product.adapter.in.web.product.request.RegisterProductRequest;
import com.personal.marketnote.product.adapter.in.web.product.request.UpdateProductRequest;
import com.personal.marketnote.product.port.in.command.*;

import java.util.List;

public class ProductRequestToCommandMapper {
    public static RegisterProductCommand mapToCommand(RegisterProductRequest registerProductRequest) {
        return RegisterProductCommand.builder()
                .sellerId(registerProductRequest.getSellerId())
                .name(registerProductRequest.getName())
                .brandName(registerProductRequest.getBrandName())
                .detail(registerProductRequest.getDetail())
                .price(registerProductRequest.getPrice())
                .discountPrice(registerProductRequest.getDiscountPrice())
                .accumulatedPoint(registerProductRequest.getAccumulatedPoint())
                .isFindAllOptions(registerProductRequest.getIsFindAllOptions())
                .tags(registerProductRequest.getTags())
                .build();
    }

    public static RegisterProductCategoriesCommand mapToCommand(
            Long productId, RegisterProductCategoriesRequest registerProductCategoriesRequest
    ) {
        return RegisterProductCategoriesCommand.of(
                productId, registerProductCategoriesRequest.getCategoryIds()
        );
    }

    public static RegisterProductOptionsCommand mapToCommand(
            Long productId, UpdateProductOptionsRequest request
    ) {
        List<RegisterProductOptionsCommand.OptionItem> optionItems = request.getOptions().stream()
                .map(o -> new RegisterProductOptionsCommand.OptionItem(o.getContent()))
                .toList();
        return RegisterProductOptionsCommand.of(productId, request.getCategoryName(), optionItems);
    }

    public static UpdateProductOptionsCommand mapToUpdateCommand(
            Long productId, Long optionCategoryId, UpdateProductOptionsRequest request
    ) {
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
        return UpdateProductCommand.builder()
                .id(id)
                .name(request.getName())
                .brandName(request.getBrandName())
                .detail(request.getDetail())
                .isFindAllOptions(request.getIsFindAllOptions())
                .tags(request.getTags())
                .build();
    }

    public static GetMyOrderingProductsQuery mapToCommand(
            GetMyOrderingProductsRequest getMyOrderingProductsRequest
    ) {
        return GetMyOrderingProductsQuery.from(
                getMyOrderingProductsRequest.orderingItemRequests()
                        .stream()
                        .map(request -> OrderingItemQuery.of(
                                request.pricePolicyId(), request.sharerId(), request.quantity(), request.imageUrl())
                        )
                        .toList()
        );
    }
}
