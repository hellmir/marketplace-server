package com.personal.marketnote.commerce.adapter.in.client.order.mapper;

import com.personal.marketnote.commerce.adapter.in.client.order.request.ChangeOrderStatusRequest;
import com.personal.marketnote.commerce.adapter.in.client.order.request.RegisterOrderRequest;
import com.personal.marketnote.commerce.port.in.command.order.ChangeOrderStatusCommand;
import com.personal.marketnote.commerce.port.in.command.order.OrderProductItem;
import com.personal.marketnote.commerce.port.in.command.order.RegisterOrderCommand;

import java.util.List;

public class OrderRequestToCommandMapper {
    public static RegisterOrderCommand mapToCommand(
            RegisterOrderRequest request,
            Long buyerId
    ) {
        List<OrderProductItem> orderProducts = request.getOrderProducts().stream()
                .map(item -> OrderProductItem.builder()
                        .pricePolicyId(item.getPricePolicyId())
                        .quantity(item.getQuantity())
                        .unitAmount(item.getUnitAmount())
                        .imageUrl(item.getImageUrl())
                        .build())
                .toList();

        return RegisterOrderCommand.builder()
                .sellerId(request.getSellerId())
                .buyerId(buyerId)
                .totalAmount(request.getTotalAmount())
                .couponAmount(request.getCouponAmount())
                .pointAmount(request.getPointAmount())
                .orderProducts(orderProducts)
                .build();
    }

    public static ChangeOrderStatusCommand mapToCommand(Long id, ChangeOrderStatusRequest request) {
        return ChangeOrderStatusCommand.builder()
                .id(id)
                .pricePolicyIds(request.getPricePolicyIds())
                .orderStatus(request.getOrderStatus())
                .reasonCategory(request.getReasonCategory())
                .reason(request.getReason())
                .build();
    }
}

