package com.personal.marketnote.commerce.port.in.result.order;

import com.personal.marketnote.commerce.domain.order.Order;
import com.personal.marketnote.commerce.port.out.result.product.GetOrderedProductResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record OrderHistoryByDateResult(
        LocalDate orderDate,
        Integer count,
        List<GetOrderResult> orders
) {
    public static OrderHistoryByDateResult of(
            LocalDate orderDate,
            List<Order> orders,
            Map<Long, GetOrderedProductResult> productSummaries
    ) {
        Map<Long, GetOrderedProductResult> summaries =
                com.personal.marketnote.common.utility.FormatValidator.hasValue(
                        productSummaries != null ? productSummaries.values() : null
                ) ? productSummaries : Map.of();
        List<GetOrderResult> orderResults = orders.stream()
                .map(order -> GetOrderResult.from(order, summaries))
                .toList();

        return new OrderHistoryByDateResult(orderDate, orderResults.size(), orderResults);
    }
}
