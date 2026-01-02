package com.personal.marketnote.product.port.in.result;

import java.math.BigDecimal;
import java.util.List;

public record PricePolicyItemResult(
        Long id,
        Long price,
        Long discountPrice,
        Long accumulatedPoint,
        BigDecimal discountRate,
        boolean basePolicy,
        List<Long> optionIds
) {
}


