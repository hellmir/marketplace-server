package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record GetFasstoWarehousingCommand(
        String customerCode,
        String accessToken,
        String startDate,
        String endDate
) {
    public static GetFasstoWarehousingCommand of(
            String customerCode,
            String accessToken,
            String startDate,
            String endDate
    ) {
        return new GetFasstoWarehousingCommand(customerCode, accessToken, startDate, endDate);
    }
}
