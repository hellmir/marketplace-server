package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record GetFasstoStocksResult(
        Integer dataCount,
        List<FasstoStockInfoResult> stocks
) {
    public static GetFasstoStocksResult of(
            Integer dataCount,
            List<FasstoStockInfoResult> stocks
    ) {
        return new GetFasstoStocksResult(dataCount, stocks);
    }
}
