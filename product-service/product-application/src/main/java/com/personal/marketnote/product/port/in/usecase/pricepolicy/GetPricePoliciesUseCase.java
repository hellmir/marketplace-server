package com.personal.marketnote.product.port.in.usecase.pricepolicy;

import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import com.personal.marketnote.product.port.in.result.pricepolicy.GetPricePoliciesResult;

import java.util.List;

public interface GetPricePoliciesUseCase {
    GetPricePoliciesResult getPricePoliciesAndOptions(Long productId);

    List<PricePolicy> getPricePoliciesAndOptions(List<Long> ids);
}
