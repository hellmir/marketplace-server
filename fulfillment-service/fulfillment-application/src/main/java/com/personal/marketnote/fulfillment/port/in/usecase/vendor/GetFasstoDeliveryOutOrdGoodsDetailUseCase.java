package com.personal.marketnote.fulfillment.port.in.usecase.vendor;

import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoDeliveryOutOrdGoodsDetailCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoDeliveryOutOrdGoodsDetailResult;

public interface GetFasstoDeliveryOutOrdGoodsDetailUseCase {
    GetFasstoDeliveryOutOrdGoodsDetailResult getOutOrdGoodsDetail(GetFasstoDeliveryOutOrdGoodsDetailCommand command);
}
