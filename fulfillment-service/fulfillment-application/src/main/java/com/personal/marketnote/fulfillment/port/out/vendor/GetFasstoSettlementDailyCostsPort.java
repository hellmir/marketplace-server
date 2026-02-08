package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.settlement.FasstoSettlementDailyCostQuery;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoSettlementDailyCostsResult;

public interface GetFasstoSettlementDailyCostsPort {
    GetFasstoSettlementDailyCostsResult getDailyCosts(FasstoSettlementDailyCostQuery query);
}
