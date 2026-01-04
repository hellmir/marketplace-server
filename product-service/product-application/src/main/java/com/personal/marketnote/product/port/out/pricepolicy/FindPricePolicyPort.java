package com.personal.marketnote.product.port.out.pricepolicy;

import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;

import java.util.List;
import java.util.Optional;

public interface FindPricePolicyPort {
    Optional<PricePolicy> findById(Long id);

    Optional<PricePolicy> findByProductAndOptionIds(Long productId, List<Long> optionIds);

    Optional<PricePolicy> findByOptionIds(List<Long> optionIds);
}


