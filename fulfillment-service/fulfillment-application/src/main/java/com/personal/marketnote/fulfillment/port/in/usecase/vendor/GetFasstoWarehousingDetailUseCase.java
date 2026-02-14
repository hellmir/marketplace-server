package com.personal.marketnote.fulfillment.port.in.usecase.vendor;

import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoWarehousingDetailCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousingDetailResult;

public interface GetFasstoWarehousingDetailUseCase {
    GetFasstoWarehousingDetailResult getWarehousingDetail(GetFasstoWarehousingDetailCommand command);
}
