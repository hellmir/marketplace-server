package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.UpdateFasstoWarehousingItemResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.UpdateFasstoWarehousingResult;

import java.util.List;

public record UpdateFasstoWarehousingResponse(
        Integer dataCount,
        List<UpdateFasstoWarehousingItemResult> warehousing
) {
    public static UpdateFasstoWarehousingResponse from(UpdateFasstoWarehousingResult result) {
        return new UpdateFasstoWarehousingResponse(result.dataCount(), result.warehousing());
    }
}
