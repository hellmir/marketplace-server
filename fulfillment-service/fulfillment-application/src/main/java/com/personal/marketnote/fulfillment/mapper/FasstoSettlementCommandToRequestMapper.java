package com.personal.marketnote.fulfillment.mapper;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.settlement.FasstoSettlementDailyCostQuery;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoSettlementDailyCostsCommand;

public class FasstoSettlementCommandToRequestMapper {
    public static FasstoSettlementDailyCostQuery mapToQuery(GetFasstoSettlementDailyCostsCommand command) {
        return FasstoSettlementDailyCostQuery.of(
                command.yearMonth(),
                command.whCd(),
                command.customerCode(),
                command.accessToken()
        );
    }
}
