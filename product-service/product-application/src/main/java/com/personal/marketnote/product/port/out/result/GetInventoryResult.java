package com.personal.marketnote.product.port.out.result;

public record GetInventoryResult(
        Long pricePolicyId,
        Integer stock
) {
    public static GetInventoryResult of(Long pricePolicyId, Integer stock) {
        return new GetInventoryResult(pricePolicyId, stock);
    }
}
