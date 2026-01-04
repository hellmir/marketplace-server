package com.personal.marketnote.product.port.in.result;

import com.personal.marketnote.common.application.file.port.in.result.GetFilesResult;

import java.util.List;

public record GetProductInfoWithOptionsResult(
        GetProductInfoResult productInfo,
        List<SelectableProductOptionCategoryItemResult> categories,
        GetFilesResult representativeImages,
        GetFilesResult contentImages
) {
    public static GetProductInfoWithOptionsResult of(
            GetProductInfoResult productInfo,
            List<SelectableProductOptionCategoryItemResult> categories
    ) {
        return new GetProductInfoWithOptionsResult(productInfo, categories, null, null);
    }

    public static GetProductInfoWithOptionsResult of(
            GetProductInfoResult productInfo,
            List<SelectableProductOptionCategoryItemResult> categories,
            GetFilesResult representativeImages,
            GetFilesResult contentImages
    ) {
        return new GetProductInfoWithOptionsResult(productInfo, categories, representativeImages, contentImages);
    }
}
