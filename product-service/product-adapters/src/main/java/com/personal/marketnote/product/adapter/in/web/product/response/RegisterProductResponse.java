package com.personal.marketnote.product.adapter.in.web.product.response;

import com.personal.marketnote.product.port.in.result.product.RegisterProductResult;
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


