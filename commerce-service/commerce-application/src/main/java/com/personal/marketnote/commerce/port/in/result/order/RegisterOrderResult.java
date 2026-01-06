package com.personal.marketnote.commerce.port.in.result.order;

import com.personal.marketnote.commerce.domain.order.Order;

public record RegisterOrderResult(
        Long id
) {
    public static RegisterOrderResult from(Order order) {
        return new RegisterOrderResult(order.getId());
    }
}

