package com.personal.marketnote.product.port.out.result;

import com.personal.marketnote.common.utility.FormatValidator;

public record GetInventoryResult(
        Long pricePolicyId,
        Integer stock
) {
    public static GetInventoryResult of(Long pricePolicyId, Integer stock) {
        return new GetInventoryResult(pricePolicyId, stock);
    }

    public boolean hasNoStock() {
        return !FormatValidator.hasValue(stock);
    }

    public boolean isMe(Long pricePolicyId) {
        return FormatValidator.equals(this.pricePolicyId, pricePolicyId);
    }
}
