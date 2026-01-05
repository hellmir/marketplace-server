package com.personal.marketnote.order.adapter.in.client.order.response;

import com.personal.marketnote.order.port.in.result.order.GetOrderResult;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetOrderResponse(
        GetOrderResult orderInfo
) {
    public static GetOrderResponse from(GetOrderResult getOrderResult) {
        return new GetOrderResponse(getOrderResult);
    }
}

