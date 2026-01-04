package com.personal.marketnote.product.port.in.usecase.pricepolicy;

import com.personal.marketnote.product.port.in.result.pricepolicy.GetPricePoliciesResult;

public interface GetPricePoliciesUseCase {
    GetPricePoliciesResult getPricePolicies(Long productId);
}


