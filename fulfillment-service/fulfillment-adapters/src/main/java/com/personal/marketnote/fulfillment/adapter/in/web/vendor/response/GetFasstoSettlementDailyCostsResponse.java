package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.FasstoSettlementDailyCostInfoResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoSettlementDailyCostsResult;

import java.util.List;

public record GetFasstoSettlementDailyCostsResponse(
        Integer dataCount,
        List<FasstoSettlementDailyCostInfoResult> dailyCosts
) {
    public static GetFasstoSettlementDailyCostsResponse from(GetFasstoSettlementDailyCostsResult result) {
        return new GetFasstoSettlementDailyCostsResponse(result.dataCount(), result.dailyCosts());
    }
}
