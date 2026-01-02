package com.personal.marketnote.product.port.in.usecase.product;

public interface DeletePricePolicyUseCase {
    void delete(Long userId, boolean isAdmin, Long productId, Long pricePolicyId);
}


