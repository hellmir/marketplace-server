package com.personal.marketnote.commerce.port.in.result.order;

import com.personal.marketnote.commerce.domain.order.Order;
import com.personal.marketnote.commerce.port.out.result.product.GetOrderedProductResult;

import java.util.List;
import java.util.Map;

public record GetOrdersDomainResult(
        List<Order> orders,
        Map<Long, GetOrderedProductResult> productSummaries
) {
    public static GetOrdersDomainResult of(
            List<Order> orders,
            Map<Long, GetOrderedProductResult> productSummaries
    ) {
        return new GetOrdersDomainResult(orders, productSummaries);
    }
}
