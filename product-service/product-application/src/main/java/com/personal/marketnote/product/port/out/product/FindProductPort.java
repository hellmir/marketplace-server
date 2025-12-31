package com.personal.marketnote.product.port.out.product;

import com.personal.marketnote.product.domain.product.Product;

import java.util.Optional;

public interface FindProductPort {
    boolean existsByIdAndSellerId(Long productId, Long sellerId);

    Optional<Product> findById(Long productId);
}
