package com.personal.marketnote.product.adapter.in.client.product.mapper;

import com.personal.marketnote.product.adapter.in.client.product.request.RegisterProductRequest;
import com.personal.marketnote.product.port.in.command.RegisterProductCommand;

public class CartRequestToCommandMapper {
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
}
