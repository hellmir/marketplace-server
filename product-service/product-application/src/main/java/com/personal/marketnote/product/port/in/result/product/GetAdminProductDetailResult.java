package com.personal.marketnote.product.port.in.result.product;

import com.personal.marketnote.product.port.in.result.fulfillment.FulfillmentVendorGoodsElementInfoResult;
import com.personal.marketnote.product.port.in.result.fulfillment.FulfillmentVendorGoodsInfoResult;

public record GetAdminProductDetailResult(
        GetProductInfoWithOptionsResult product,
        FulfillmentVendorGoodsInfoResult fasstoGoodsInfo,
        FulfillmentVendorGoodsElementInfoResult fasstoGoodsElement
) {
    public static GetAdminProductDetailResult of(
            GetProductInfoWithOptionsResult product,
            FulfillmentVendorGoodsInfoResult fasstoGoodsInfo,
            FulfillmentVendorGoodsElementInfoResult fasstoGoodsElement
    ) {
        return new GetAdminProductDetailResult(product, fasstoGoodsInfo, fasstoGoodsElement);
    }
}
