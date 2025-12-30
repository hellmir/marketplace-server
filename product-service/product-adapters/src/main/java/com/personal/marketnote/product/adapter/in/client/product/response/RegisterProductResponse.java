package com.personal.marketnote.product.adapter.in.client.product.response;

import com.personal.marketnote.product.port.in.result.RegisterProductResult;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record RegisterProductResponse(
        Long id
) {
    public static RegisterProductResponse from(RegisterProductResult result) {
        return new RegisterProductResponse(result.id());
    }
}


