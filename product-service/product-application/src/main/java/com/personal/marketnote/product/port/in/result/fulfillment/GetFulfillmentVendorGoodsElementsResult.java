package com.personal.marketnote.product.port.in.result.fulfillment;

import java.util.List;

public record GetFulfillmentVendorGoodsElementsResult(
        Integer dataCount,
        List<FulfillmentVendorGoodsElementInfoResult> elements
) {
    public static GetFulfillmentVendorGoodsElementsResult of(
            Integer dataCount,
            List<FulfillmentVendorGoodsElementInfoResult> elements
    ) {
        return new GetFulfillmentVendorGoodsElementsResult(dataCount, elements);
    }
}
