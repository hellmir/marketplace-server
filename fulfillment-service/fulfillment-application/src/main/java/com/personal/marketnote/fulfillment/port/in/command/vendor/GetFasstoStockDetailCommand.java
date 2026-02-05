package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record GetFasstoStockDetailCommand(
        String customerCode,
        String accessToken,
        String cstGodCd,
        String outOfStockYn
) {
    public static GetFasstoStockDetailCommand of(
            String customerCode,
            String accessToken,
            String cstGodCd,
            String outOfStockYn
    ) {
        return new GetFasstoStockDetailCommand(customerCode, accessToken, cstGodCd, outOfStockYn);
    }
}
