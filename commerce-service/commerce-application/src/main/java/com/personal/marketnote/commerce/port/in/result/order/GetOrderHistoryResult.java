package com.personal.marketnote.commerce.port.in.result.order;

import com.personal.marketnote.commerce.domain.order.Order;
import com.personal.marketnote.commerce.port.out.result.product.ProductInfoResult;
import com.personal.marketnote.common.utility.FormatValidator;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Builder(access = AccessLevel.PRIVATE)
public record GetOrderHistoryResult(
        List<OrderHistoryByDateResult> orderHistory
) {
    public static GetOrderHistoryResult from(
            List<Order> orders,
            Map<Long, ProductInfoResult> productSummaries
    ) {
        Map<Long, ProductInfoResult> summaries =
                com.personal.marketnote.common.utility.FormatValidator.hasValue(
                        productSummaries != null ? productSummaries.values() : null
                ) ? productSummaries : Map.of();
        Map<LocalDate, List<Order>> grouped = new LinkedHashMap<>();

        for (Order order : orders) {
            LocalDate orderDate = FormatValidator.hasValue(order.getCreatedAt())
                    ? order.getCreatedAt().toLocalDate()
                    : LocalDate.now();

            grouped.computeIfAbsent(orderDate, key -> new ArrayList<>())
                    .add(order);
        }

        List<OrderHistoryByDateResult> histories = grouped.entrySet().stream()
                .map(entry -> OrderHistoryByDateResult.of(entry.getKey(), entry.getValue(), summaries))
                .toList();

        return new GetOrderHistoryResult(histories);
    }
}
