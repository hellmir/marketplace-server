package com.personal.marketnote.product.port.out.pricepolicy;

public interface FindPricePolicyPort {
    boolean existsByIdAndProductId(Long pricePolicyId, Long productId);
}


