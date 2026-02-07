package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record GetFasstoStocksCommand(
        String customerCode,
        String accessToken,
        String outOfStockYn
) {
    public static GetFasstoStocksCommand of(
            String customerCode,
            String accessToken
    ) {
        return new GetFasstoStocksCommand(customerCode, accessToken, null);
    }

    public static GetFasstoStocksCommand of(
            String customerCode,
            String accessToken,
            String outOfStockYn
    ) {
        return new GetFasstoStocksCommand(customerCode, accessToken, outOfStockYn);
    }
}
