package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record GetFasstoStocksCommand(
        String customerCode,
        String accessToken,
        String outOfStockYn,
        String whCd
) {
    public static GetFasstoStocksCommand of(
            String customerCode,
            String accessToken
    ) {
        return new GetFasstoStocksCommand(customerCode, accessToken, null, null);
    }

    public static GetFasstoStocksCommand of(
            String customerCode,
            String accessToken,
            String outOfStockYn
    ) {
        return new GetFasstoStocksCommand(customerCode, accessToken, outOfStockYn, null);
    }

    public static GetFasstoStocksCommand of(
            String customerCode,
            String accessToken,
            String outOfStockYn,
            String whCd
    ) {
        return new GetFasstoStocksCommand(customerCode, accessToken, outOfStockYn, whCd);
    }
}
