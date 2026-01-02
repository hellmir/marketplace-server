package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.port.in.result.GetPricePoliciesResult;

public interface GetPricePoliciesUseCase {
    GetPricePoliciesResult getPricePolicies(Long productId);
}


