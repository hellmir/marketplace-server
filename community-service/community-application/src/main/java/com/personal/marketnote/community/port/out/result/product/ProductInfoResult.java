package com.personal.marketnote.community.port.out.result.product;

import java.util.List;

public record ProductInfoResult(
        String name,
        String brandName,
        List<ProductOptionInfoResult> selectedOptions
) {
}
