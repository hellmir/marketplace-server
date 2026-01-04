package com.personal.marketnote.product.port.in.usecase.pricepolicy;

import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;

public interface GetPricePolicyUseCase {
    PricePolicy getPricePolicy(Long id);
}


