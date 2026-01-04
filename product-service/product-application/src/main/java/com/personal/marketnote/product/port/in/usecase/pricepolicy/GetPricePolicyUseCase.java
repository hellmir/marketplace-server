package com.personal.marketnote.product.port.in.usecase.pricepolicy;

import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;

import java.util.List;

public interface GetPricePolicyUseCase {
    PricePolicy getPricePolicy(Long id);

    PricePolicy getPricePolicy(List<Long> optionIds);
}


