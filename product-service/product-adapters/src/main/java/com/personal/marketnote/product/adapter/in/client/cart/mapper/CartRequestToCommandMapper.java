package com.personal.marketnote.product.adapter.in.client.cart.mapper;

import com.personal.marketnote.product.adapter.in.client.cart.request.AddCartProductRequest;
import com.personal.marketnote.product.adapter.in.client.cart.request.UpdateCartProductQuantityRequest;
import com.personal.marketnote.product.port.in.command.AddCartProductCommand;
import com.personal.marketnote.product.port.in.command.UpdateCartProductQuantityCommand;

public class CartRequestToCommandMapper {
    public static AddCartProductCommand mapToCommand(Long userId, AddCartProductRequest request) {
        return AddCartProductCommand.of(
                userId,
                request.pricePolicyId(),
                request.imageUrl(),
                request.quantity()
        );
    }

    public static UpdateCartProductQuantityCommand mapToCommand(Long userId, UpdateCartProductQuantityRequest request) {
        return UpdateCartProductQuantityCommand.of(
                userId,
                request.pricePolicyId(),
                request.newQuantity()
        );
    }
}
