package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoWarehousingItemResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoWarehousingResult;

import java.util.List;

public record RegisterFasstoWarehousingResponse(
        Integer dataCount,
        List<RegisterFasstoWarehousingItemResult> warehousing
) {
    public static RegisterFasstoWarehousingResponse from(RegisterFasstoWarehousingResult result) {
        return new RegisterFasstoWarehousingResponse(result.dataCount(), result.warehousing());
    }
}
