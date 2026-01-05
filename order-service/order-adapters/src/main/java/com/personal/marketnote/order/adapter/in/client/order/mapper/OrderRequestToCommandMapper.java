package com.personal.marketnote.order.adapter.in.client.order.mapper;

import com.personal.marketnote.order.adapter.in.client.order.request.RegisterOrderRequest;
import com.personal.marketnote.order.port.in.command.RegisterOrderCommand;

import java.util.List;

public class OrderRequestToCommandMapper {
    public static RegisterOrderCommand mapToCommand(
            RegisterOrderRequest request,
            Long buyerId
    ) {
        List<RegisterOrderCommand.OrderProductItem> orderProducts = request.getOrderProducts().stream()
                .map(item -> new RegisterOrderCommand.OrderProductItem(
                        item.getPricePolicyId(),
                        item.getQuantity(),
                        item.getUnitAmount(),
                        item.getImageUrl()
                ))
                .toList();

        return RegisterOrderCommand.of(
                request.getSellerId(),
                buyerId,
                request.getTotalAmount(),
                request.getCouponAmount(),
                request.getPointAmount(),
                orderProducts
        );
    }
}

