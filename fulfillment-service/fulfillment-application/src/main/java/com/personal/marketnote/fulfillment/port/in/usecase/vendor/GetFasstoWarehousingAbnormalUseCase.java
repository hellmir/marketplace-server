package com.personal.marketnote.fulfillment.port.in.usecase.vendor;

import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoWarehousingAbnormalCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousingAbnormalResult;

public interface GetFasstoWarehousingAbnormalUseCase {
    GetFasstoWarehousingAbnormalResult getWarehousingAbnormal(GetFasstoWarehousingAbnormalCommand command);
}
