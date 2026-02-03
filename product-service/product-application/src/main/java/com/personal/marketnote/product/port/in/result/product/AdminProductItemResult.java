package com.personal.marketnote.product.port.in.result.product;

import com.personal.marketnote.product.port.in.result.fulfillment.FulfillmentVendorGoodsInfoResult;

public record AdminProductItemResult(
        ProductItemResult product,
        FulfillmentVendorGoodsInfoResult fasstoGoodsInfo
) {
    public static AdminProductItemResult of(
            ProductItemResult product,
            FulfillmentVendorGoodsInfoResult fasstoGoodsInfo
    ) {
        return new AdminProductItemResult(product, fasstoGoodsInfo);
    }
}
