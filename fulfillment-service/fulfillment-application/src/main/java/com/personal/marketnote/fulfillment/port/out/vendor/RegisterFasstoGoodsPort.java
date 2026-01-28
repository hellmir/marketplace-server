package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.goods.FasstoGoodsMapper;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoGoodsResult;

public interface RegisterFasstoGoodsPort {
    RegisterFasstoGoodsResult registerGoods(FasstoGoodsMapper request);
}
