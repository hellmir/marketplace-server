package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.warehousing.FasstoWarehousingQuery;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousingResult;

public interface GetFasstoWarehousingPort {
    GetFasstoWarehousingResult getWarehousing(FasstoWarehousingQuery query);
}
