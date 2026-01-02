package com.personal.marketnote.product.port.in.result;

import java.util.List;

public record GetProductInfoWithOptionsResult(
        GetProductInfoResult productInfo,
        List<SelectableProductOptionCategoryItemResult> categories
) {
    public static GetProductInfoWithOptionsResult of(
            GetProductInfoResult productInfo,
            List<SelectableProductOptionCategoryItemResult> categories
    ) {
        return new GetProductInfoWithOptionsResult(productInfo, categories);
    }
}
