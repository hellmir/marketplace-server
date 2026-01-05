package com.personal.marketnote.order.port.in.result.order;

import com.personal.marketnote.product.domain.order.Order;

public record RegisterOrderResult(
        Long id
) {
    public static RegisterOrderResult from(Order order) {
        return new RegisterOrderResult(order.getId());
    }
}

