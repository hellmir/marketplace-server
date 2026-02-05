package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.goods.FasstoGoodsDetailQuery;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoGoodsResult;

public interface GetFasstoGoodsDetailPort {
    GetFasstoGoodsResult getGoodsDetail(FasstoGoodsDetailQuery query);
}
