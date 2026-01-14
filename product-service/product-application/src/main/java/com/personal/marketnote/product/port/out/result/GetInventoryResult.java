package com.personal.marketnote.product.port.out.result;

import com.personal.marketnote.common.utility.FormatConverter;
import com.personal.marketnote.common.utility.FormatValidator;

import static com.personal.marketnote.common.utility.NumberConstant.MINUS_ONE;

public record GetInventoryResult(
        Long pricePolicyId,
        Integer stock
) {
    public static GetInventoryResult of(Long pricePolicyId, Integer stock) {
        return new GetInventoryResult(pricePolicyId, stock);
    }

    public static GetInventoryResult generateResultWithoutStock(Long pricePolicyId) {
        return new GetInventoryResult(pricePolicyId, FormatConverter.parseToInteger(MINUS_ONE));
    }

    public boolean hasNoStock() {
        return !FormatValidator.hasValue(stock);
    }

    public boolean isMe(Long pricePolicyId) {
        return FormatValidator.equals(this.pricePolicyId, pricePolicyId);
    }
}
