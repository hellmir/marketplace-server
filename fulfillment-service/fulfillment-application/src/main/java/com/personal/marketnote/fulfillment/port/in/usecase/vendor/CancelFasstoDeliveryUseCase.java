package com.personal.marketnote.fulfillment.port.in.usecase.vendor;

import com.personal.marketnote.fulfillment.port.in.command.vendor.CancelFasstoDeliveryCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.CancelFasstoDeliveryResult;

public interface CancelFasstoDeliveryUseCase {
    CancelFasstoDeliveryResult cancelDelivery(CancelFasstoDeliveryCommand command);
}
