package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.FasstoWarehousingAbnormalInfoResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousingAbnormalResult;

import java.util.List;

public record GetFasstoWarehousingAbnormalResponse(
        Integer dataCount,
        List<FasstoWarehousingAbnormalInfoResult> abnormals
) {
    public static GetFasstoWarehousingAbnormalResponse from(GetFasstoWarehousingAbnormalResult result) {
        return new GetFasstoWarehousingAbnormalResponse(result.dataCount(), result.abnormals());
    }
}
