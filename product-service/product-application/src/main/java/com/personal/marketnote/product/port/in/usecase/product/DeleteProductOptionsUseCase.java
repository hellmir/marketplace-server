package com.personal.marketnote.product.port.in.usecase.product;

public interface DeleteProductOptionsUseCase {
    void deleteProductOptions(
            Long userId, boolean isAdmin, Long productId, Long optionCategoryId
    );
}


