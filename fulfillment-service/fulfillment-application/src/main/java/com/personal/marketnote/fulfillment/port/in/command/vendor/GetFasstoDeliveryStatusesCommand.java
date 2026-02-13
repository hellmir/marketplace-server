package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record GetFasstoDeliveryStatusesCommand(
        String customerCode,
        String accessToken,
        String startDate,
        String endDate,
        String outDiv
) {
    public static GetFasstoDeliveryStatusesCommand of(
            String customerCode,
            String accessToken,
            String startDate,
            String endDate,
            String outDiv
    ) {
        return new GetFasstoDeliveryStatusesCommand(customerCode, accessToken, startDate, endDate, outDiv);
    }
}
