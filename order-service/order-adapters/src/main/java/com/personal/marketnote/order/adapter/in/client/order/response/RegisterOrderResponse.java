package com.personal.marketnote.order.adapter.in.client.order.response;

import com.personal.marketnote.order.port.in.result.order.RegisterOrderResult;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record RegisterOrderResponse(
        Long id
) {
    public static RegisterOrderResponse from(RegisterOrderResult result) {
        return new RegisterOrderResponse(result.id());
    }
}

