package com.personal.marketnote.commerce.adapter.in.client.order.response;

import com.personal.marketnote.commerce.port.in.result.order.GetBuyerOrderCountResult;

public record GetMyOrderCountResponse(long totalCount) {
    public static GetMyOrderCountResponse from(GetBuyerOrderCountResult result) {
        return new GetMyOrderCountResponse(result.totalCount());
    }
}
