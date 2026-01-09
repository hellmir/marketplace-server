package com.personal.marketnote.commerce.adapter.in.client.order.response;

import com.personal.marketnote.commerce.port.in.result.order.GetOrderHistoryResult;

import java.util.List;

public record GetOrdersResponse(
        List<OrderHistoryByDateResponse> orderHistory
) {
    public static GetOrdersResponse from(GetOrderHistoryResult getOrderHistoryResult) {
        return new GetOrdersResponse(
                getOrderHistoryResult.orderHistory().stream()
                        .map(OrderHistoryByDateResponse::from)
                        .toList()
        );
    }
}
