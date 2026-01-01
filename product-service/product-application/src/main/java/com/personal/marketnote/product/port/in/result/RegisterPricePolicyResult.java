package com.personal.marketnote.product.port.in.result;

public record RegisterPricePolicyResult(Long id) {
    public static RegisterPricePolicyResult of(Long id) {
        return new RegisterPricePolicyResult(id);
    }
}


