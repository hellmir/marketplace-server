package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record GetFasstoShopsCommand(
        String customerCode,
        String accessToken
) {
    public static GetFasstoShopsCommand of(String customerCode, String accessToken) {
        return new GetFasstoShopsCommand(customerCode, accessToken);
    }
}
