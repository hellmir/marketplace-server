package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record GetFasstoGoodsElementsCommand(
        String customerCode,
        String accessToken
) {
    public static GetFasstoGoodsElementsCommand of(String customerCode, String accessToken) {
        return new GetFasstoGoodsElementsCommand(customerCode, accessToken);
    }
}
