package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.FasstoWarehousingInfoResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousingResult;

import java.util.List;

public record GetFasstoWarehousingResponse(
        Integer dataCount,
        List<FasstoWarehousingInfoResult> warehousing
) {
    public static GetFasstoWarehousingResponse from(GetFasstoWarehousingResult result) {
        return new GetFasstoWarehousingResponse(result.dataCount(), result.warehousing());
    }
}
