package com.personal.marketnote.commerce.adapter.in.client.order.response;

import com.personal.marketnote.commerce.port.in.result.order.GetOrdersResult;

import java.util.List;

public record GetOrdersResponse(
        List<OrderHistoryByDateResponse> orderHistories
) {
    public static GetOrdersResponse from(GetOrdersResult getOrdersResult) {
        return new GetOrdersResponse(
                getOrdersResult.orderHistories().stream()
                        .map(OrderHistoryByDateResponse::from)
                        .toList()
        );
    }
}
