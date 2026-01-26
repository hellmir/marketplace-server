package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record GetFasstoSuppliersCommand(
        String customerCode,
        String accessToken
) {
    public static GetFasstoSuppliersCommand of(String customerCode, String accessToken) {
        return new GetFasstoSuppliersCommand(customerCode, accessToken);
    }
}
