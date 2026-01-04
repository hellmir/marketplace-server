package com.personal.marketnote.product.port.out.pricepolicy;

import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;

import java.util.List;

public interface FindPricePoliciesPort {
    List<PricePolicy> findByProductId(Long productId);
}
