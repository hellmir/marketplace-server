package com.personal.marketnote.product.port.in.result.product;

import com.personal.marketnote.product.domain.product.Product;

public record RegisterProductResult(
        Long id
) {
    public static RegisterProductResult from(Product product) {
        return new RegisterProductResult(product.getId());
    }
}
