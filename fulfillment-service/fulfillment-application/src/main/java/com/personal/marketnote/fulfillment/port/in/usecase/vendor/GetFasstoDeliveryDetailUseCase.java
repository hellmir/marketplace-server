package com.personal.marketnote.fulfillment.port.in.usecase.vendor;

import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoDeliveryDetailCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoDeliveryDetailResult;

public interface GetFasstoDeliveryDetailUseCase {
    GetFasstoDeliveryDetailResult getDeliveryDetail(GetFasstoDeliveryDetailCommand command);
}
