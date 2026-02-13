package com.personal.marketnote.fulfillment.port.in.usecase.vendor;

import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoDeliveryStatusesCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoDeliveryStatusesResult;

public interface GetFasstoDeliveryStatusesUseCase {
    GetFasstoDeliveryStatusesResult getDeliveryStatuses(GetFasstoDeliveryStatusesCommand command);
}
