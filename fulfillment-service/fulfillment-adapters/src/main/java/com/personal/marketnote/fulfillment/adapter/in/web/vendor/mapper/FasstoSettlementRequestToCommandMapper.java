package com.personal.marketnote.fulfillment.adapter.in.web.vendor.mapper;

import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoSettlementDailyCostsCommand;

public class FasstoSettlementRequestToCommandMapper {
    public static GetFasstoSettlementDailyCostsCommand mapToDailyCostsCommand(
            String yearMonth,
            String whCd,
            String customerCode,
            String accessToken
    ) {
        return GetFasstoSettlementDailyCostsCommand.of(yearMonth, whCd, customerCode, accessToken);
    }
}
