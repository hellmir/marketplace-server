package com.personal.marketnote.product.port.out.pricepolicy;

import java.math.BigDecimal;
import java.util.List;

public interface FindPricePoliciesPort {
    record PricePolicyWithOptions(
            Long id,
            Long price,
            Long discountPrice,
            Long accumulatedPoint,
            BigDecimal discountRate,
            List<Long> optionIds
    ) {
    }

    List<PricePolicyWithOptions> findByProductId(Long productId);
}
