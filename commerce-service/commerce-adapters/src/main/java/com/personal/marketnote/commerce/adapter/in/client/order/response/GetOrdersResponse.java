package com.personal.marketnote.commerce.adapter.in.client.order.response;

import com.personal.marketnote.commerce.port.in.result.order.GetOrderResult;
import com.personal.marketnote.commerce.port.in.result.order.GetOrdersResult;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record GetOrdersResponse(
        List<GetOrderResult> orders
) {
    public static GetOrdersResponse from(GetOrdersResult getOrdersResult) {
        return new GetOrdersResponse(getOrdersResult.orders());
    }
}
