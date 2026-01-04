package com.personal.marketnote.product.adapter.in.client.product.mapper;

import com.personal.marketnote.product.adapter.in.client.product.request.RegisterProductRequest;
import com.personal.marketnote.product.port.in.command.RegisterProductCommand;

public class CartRequestToCommandMapper {
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
}
