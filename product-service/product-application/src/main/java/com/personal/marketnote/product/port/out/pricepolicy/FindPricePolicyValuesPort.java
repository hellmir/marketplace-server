package com.personal.marketnote.product.port.out.pricepolicy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface FindPricePolicyValuesPort {
    record PricePolicyValues(Long price, Long discountPrice, Long accumulatedPoint, BigDecimal discountRate) {
    }

    Optional<PricePolicyValues> findByProductAndOptionIds(Long productId, List<Long> optionIds);
}


