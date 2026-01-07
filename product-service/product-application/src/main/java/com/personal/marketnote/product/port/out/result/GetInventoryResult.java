package com.personal.marketnote.product.port.out.result;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetInventoryResult(
        Long pricePolicyId,
        Integer stock
) {
}
