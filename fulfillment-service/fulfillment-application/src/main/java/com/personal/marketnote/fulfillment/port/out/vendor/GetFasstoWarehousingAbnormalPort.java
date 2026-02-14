package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.warehousing.FasstoWarehousingAbnormalQuery;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousingAbnormalResult;

public interface GetFasstoWarehousingAbnormalPort {
    GetFasstoWarehousingAbnormalResult getWarehousingAbnormal(FasstoWarehousingAbnormalQuery query);
}
