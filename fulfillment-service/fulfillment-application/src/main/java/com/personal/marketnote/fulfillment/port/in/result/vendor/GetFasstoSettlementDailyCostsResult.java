package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record GetFasstoSettlementDailyCostsResult(
        Integer dataCount,
        List<FasstoSettlementDailyCostInfoResult> dailyCosts
) {
    public static GetFasstoSettlementDailyCostsResult of(
            Integer dataCount,
            List<FasstoSettlementDailyCostInfoResult> dailyCosts
    ) {
        return new GetFasstoSettlementDailyCostsResult(dataCount, dailyCosts);
    }
}
