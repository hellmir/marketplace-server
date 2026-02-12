package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.delivery.FasstoDeliveryOutOrdGoodsDetailQuery;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoDeliveryOutOrdGoodsDetailResult;

public interface GetFasstoDeliveryOutOrdGoodsDetailPort {
    GetFasstoDeliveryOutOrdGoodsDetailResult getOutOrdGoodsDetail(FasstoDeliveryOutOrdGoodsDetailQuery query);
}
