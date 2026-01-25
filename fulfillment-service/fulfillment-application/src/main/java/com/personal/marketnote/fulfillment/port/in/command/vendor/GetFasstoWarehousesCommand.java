package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record GetFasstoWarehousesCommand(
        String customerCode,
        String accessToken
) {
    public static GetFasstoWarehousesCommand of(String customerCode, String accessToken) {
        return new GetFasstoWarehousesCommand(customerCode, accessToken);
    }
}
