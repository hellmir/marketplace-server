package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record GetFasstoDeliveriesCommand(
        String customerCode,
        String accessToken,
        String startDate,
        String endDate,
        String status,
        String outDiv,
        String ordNo
) {
    public static GetFasstoDeliveriesCommand of(
            String customerCode,
            String accessToken,
            String startDate,
            String endDate,
            String status,
            String outDiv
    ) {
        return new GetFasstoDeliveriesCommand(customerCode, accessToken, startDate, endDate, status, outDiv, null);
    }

    public static GetFasstoDeliveriesCommand of(
            String customerCode,
            String accessToken,
            String startDate,
            String endDate,
            String status,
            String outDiv,
            String ordNo
    ) {
        return new GetFasstoDeliveriesCommand(customerCode, accessToken, startDate, endDate, status, outDiv, ordNo);
    }
}
