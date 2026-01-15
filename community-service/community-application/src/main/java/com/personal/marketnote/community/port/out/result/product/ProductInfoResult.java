package com.personal.marketnote.community.port.out.result.product;

import com.personal.marketnote.common.utility.FormatValidator;

import java.util.List;

public record ProductInfoResult(
        Long sellerId,
        String name,
        String brandName,
        List<ProductOptionInfoResult> selectedOptions
) {
    public boolean isMyProduct(Long userId) {
        return FormatValidator.equals(userId, sellerId);
    }
}
