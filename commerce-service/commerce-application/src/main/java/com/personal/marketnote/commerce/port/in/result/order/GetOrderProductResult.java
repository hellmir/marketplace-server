package com.personal.marketnote.commerce.port.in.result.order;

import com.personal.marketnote.commerce.domain.order.OrderProduct;
import com.personal.marketnote.commerce.domain.order.OrderStatus;

public record GetOrderProductResult(
        Long pricePolicyId,
        Integer quantity,
        Long unitAmount,
        String imageUrl,
        OrderStatus orderStatus
) {
    public static GetOrderProductResult from(OrderProduct orderProduct) {
        return new GetOrderProductResult(
                orderProduct.getPricePolicyId(),
                orderProduct.getQuantity(),
                orderProduct.getUnitAmount(),
                orderProduct.getImageUrl(),
                orderProduct.getOrderStatus()
        );
    }
}
