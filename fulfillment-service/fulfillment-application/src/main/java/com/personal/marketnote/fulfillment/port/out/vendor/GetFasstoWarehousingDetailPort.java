package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.warehousing.FasstoWarehousingDetailQuery;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousingDetailResult;

public interface GetFasstoWarehousingDetailPort {
    GetFasstoWarehousingDetailResult getWarehousingDetail(FasstoWarehousingDetailQuery query);
}
