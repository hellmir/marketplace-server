package com.personal.marketnote.commerce.port.in.result.order;

import com.personal.marketnote.product.domain.order.Order;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record GetOrdersResult(
        List<GetOrderResult> orders
) {
    public static GetOrdersResult from(List<Order> orders) {
        return new GetOrdersResult(
                orders.stream()
                        .map(GetOrderResult::from)
                        .toList()
        );
    }
}
