package com.personal.marketnote.product.port.in.result.pricepolicy;

import java.util.List;

public record GetPricePoliciesResult(
        List<GetProductPricePolicyResult> policies
) {
    public static GetPricePoliciesResult of(List<GetProductPricePolicyResult> policies) {
        return new GetPricePoliciesResult(policies);
    }
}


