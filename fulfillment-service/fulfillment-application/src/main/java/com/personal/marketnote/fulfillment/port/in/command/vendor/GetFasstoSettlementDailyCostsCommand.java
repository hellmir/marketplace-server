package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record GetFasstoSettlementDailyCostsCommand(
        String yearMonth,
        String whCd,
        String customerCode,
        String accessToken
) {
    public static GetFasstoSettlementDailyCostsCommand of(
            String yearMonth,
            String whCd,
            String customerCode,
            String accessToken
    ) {
        return new GetFasstoSettlementDailyCostsCommand(yearMonth, whCd, customerCode, accessToken);
    }
}
