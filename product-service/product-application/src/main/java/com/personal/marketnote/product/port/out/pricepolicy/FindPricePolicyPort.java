package com.personal.marketnote.product.port.out.pricepolicy;

import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;

import java.util.List;
import java.util.Optional;

public interface FindPricePolicyPort {
    boolean existsByIdAndProductId(Long pricePolicyId, Long productId);

    Optional<PricePolicy> findByProductAndOptionIds(Long productId, List<Long> optionIds);
}


