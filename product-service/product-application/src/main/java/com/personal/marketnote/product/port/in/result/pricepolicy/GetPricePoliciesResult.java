package com.personal.marketnote.product.port.in.result.pricepolicy;

import java.util.List;

public record GetPricePoliciesResult(
        List<PricePolicyItemResult> policies
) {
    public static GetPricePoliciesResult of(List<PricePolicyItemResult> policies) {
        return new GetPricePoliciesResult(policies);
    }
}


