package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.FasstoWarehousingDetailInfoResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousingDetailResult;

import java.util.List;

public record GetFasstoWarehousingDetailResponse(
        Integer dataCount,
        List<FasstoWarehousingDetailInfoResult> details
) {
    public static GetFasstoWarehousingDetailResponse from(GetFasstoWarehousingDetailResult result) {
        return new GetFasstoWarehousingDetailResponse(result.dataCount(), result.details());
    }
}
