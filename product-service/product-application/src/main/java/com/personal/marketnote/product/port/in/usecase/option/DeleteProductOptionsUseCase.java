package com.personal.marketnote.product.port.in.usecase.option;

public interface DeleteProductOptionsUseCase {
    void deleteProductOptions(
            Long userId, boolean isAdmin, Long productId, Long optionCategoryId
    );
}
