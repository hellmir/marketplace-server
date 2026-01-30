package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.goods.FasstoGoodsQuery;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoGoodsResult;

public interface GetFasstoGoodsPort {
    GetFasstoGoodsResult getGoods(FasstoGoodsQuery query);
}
