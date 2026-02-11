package com.personal.marketnote.fulfillment.port.in.usecase.vendor;

import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoDeliveriesCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoDeliveriesResult;

public interface GetFasstoDeliveriesUseCase {
    GetFasstoDeliveriesResult getDeliveries(GetFasstoDeliveriesCommand command);
}
