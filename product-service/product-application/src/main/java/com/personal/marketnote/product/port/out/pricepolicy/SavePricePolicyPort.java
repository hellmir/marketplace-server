package com.personal.marketnote.product.port.out.pricepolicy;

import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;

public interface SavePricePolicyPort {
    Long save(PricePolicy pricePolicy);
}
