package com.personal.marketnote.product.port.out.fulfillment;

import com.personal.marketnote.product.port.in.result.fulfillment.GetFulfillmentVendorGoodsResult;

public interface GetFulfillmentVendorGoodsPort {
    GetFulfillmentVendorGoodsResult getFulfillmentVendorGoods(String godNm);
}
