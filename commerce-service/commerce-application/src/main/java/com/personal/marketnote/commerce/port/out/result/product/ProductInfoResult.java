package com.personal.marketnote.commerce.port.out.result.product;

import com.personal.marketnote.commerce.port.out.product.result.ProductOptionInfoResult;

import java.util.List;

public record ProductInfoResult(
        String name,
        String brandName,
        List<ProductOptionInfoResult> selectedOptions
) {
}
