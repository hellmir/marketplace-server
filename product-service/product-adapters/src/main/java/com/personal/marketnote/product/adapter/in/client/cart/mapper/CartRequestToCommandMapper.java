package com.personal.marketnote.product.adapter.in.client.cart.mapper;

import com.personal.marketnote.product.adapter.in.client.cart.request.AddCartProductRequest;
import com.personal.marketnote.product.port.in.command.AddCartProductCommand;

public class CartRequestToCommandMapper {
    public static AddCartProductCommand mapToCommand(Long userId, AddCartProductRequest request) {
        return AddCartProductCommand.of(
                userId,
                request.productId(),
                request.pricePolicyId(),
                request.quantity()
        );
    }
}
