package com.personal.marketnote.product.port.in.result;

import com.personal.marketnote.product.domain.product.Product;

import java.util.List;
import java.util.stream.Collectors;

public record GetProductsResult(
        List<ProductItemResult> products
) {
    public static GetProductsResult from(List<Product> products) {
        return new GetProductsResult(
                products.stream()
                        .map(ProductItemResult::from)
                        .collect(Collectors.toList())
        );
    }
}


