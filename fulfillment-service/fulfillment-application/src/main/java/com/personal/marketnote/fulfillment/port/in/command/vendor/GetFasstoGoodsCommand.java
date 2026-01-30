package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record GetFasstoGoodsCommand(
        String customerCode,
        String accessToken
) {
    public static GetFasstoGoodsCommand of(String customerCode, String accessToken) {
        return new GetFasstoGoodsCommand(customerCode, accessToken);
    }
}
