package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.goods.FasstoGoodsElementQuery;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoGoodsElementsResult;

public interface GetFasstoGoodsElementsPort {
    GetFasstoGoodsElementsResult getGoodsElements(FasstoGoodsElementQuery query);
}
