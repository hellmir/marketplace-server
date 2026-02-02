package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.warehousing.FasstoWarehousingMapper;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoWarehousingResult;

public interface RegisterFasstoWarehousingPort {
    RegisterFasstoWarehousingResult registerWarehousing(FasstoWarehousingMapper request);
}
