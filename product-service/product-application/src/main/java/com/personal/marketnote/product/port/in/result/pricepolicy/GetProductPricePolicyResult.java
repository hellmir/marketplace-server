package com.personal.marketnote.product.port.in.result.pricepolicy;

import com.personal.marketnote.common.utility.FormatValidator;

import java.math.BigDecimal;
import java.util.List;

public record GetProductPricePolicyResult(
        Long id,
        Long price,
        Long discountPrice,
        Long accumulatedPoint,
        BigDecimal discountRate,
        List<Long> optionIds
) {
    public boolean hasOptions() {
        return FormatValidator.hasValue(optionIds);
    }
}
