package com.personal.marketnote.product.port.in.result.cart;

import com.personal.marketnote.product.domain.product.Product;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CartProductItemResult(
        Long id,
        String name,
        String brandName,
        String imageUrl,
        String status
) {
    public static CartProductItemResult from(Product product, String imageUrl) {
        return CartProductItemResult.builder()
                .id(product.getId())
                .name(product.getName())
                .brandName(product.getBrandName())
                .imageUrl(imageUrl)
                .status(product.getStatus().name())
                .build();
    }
}
