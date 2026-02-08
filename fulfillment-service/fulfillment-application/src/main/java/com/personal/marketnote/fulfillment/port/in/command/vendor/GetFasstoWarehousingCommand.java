package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record GetFasstoWarehousingCommand(
        String customerCode,
        String accessToken,
        String startDate,
        String endDate,
        String inWay,
        String ordNo,
        String wrkStat
) {
    public static GetFasstoWarehousingCommand of(
            String customerCode,
            String accessToken,
            String startDate,
            String endDate
    ) {
        return new GetFasstoWarehousingCommand(customerCode, accessToken, startDate, endDate, null, null, null);
    }

    public static GetFasstoWarehousingCommand of(
            String customerCode,
            String accessToken,
            String startDate,
            String endDate,
            String inWay,
            String ordNo,
            String wrkStat
    ) {
        return new GetFasstoWarehousingCommand(customerCode, accessToken, startDate, endDate, inWay, ordNo, wrkStat);
    }
}
