package com.personal.marketnote.commerce.adapter.in.client.order.response;

import com.personal.marketnote.commerce.port.in.result.order.GetOrderCountResult;

public record GetOrdersCountResponse(long totalCount) {
    public static GetOrdersCountResponse from(GetOrderCountResult result) {
        return new GetOrdersCountResponse(result.totalCount());
    }
}
