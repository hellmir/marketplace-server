package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record GetFasstoGoodsDetailCommand(
        String customerCode,
        String accessToken,
        String godNm
) {
    public static GetFasstoGoodsDetailCommand of(
            String customerCode,
            String accessToken,
            String godNm
    ) {
        return new GetFasstoGoodsDetailCommand(customerCode, accessToken, godNm);
    }
}
