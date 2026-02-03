package com.personal.marketnote.fulfillment.adapter.in.web.vendor.response;

import com.personal.marketnote.fulfillment.port.in.result.vendor.FasstoStockInfoResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoStocksResult;

import java.util.List;

public record GetFasstoStocksResponse(
        Integer dataCount,
        List<FasstoStockInfoResult> stocks
) {
    public static GetFasstoStocksResponse from(GetFasstoStocksResult result) {
        return new GetFasstoStocksResponse(result.dataCount(), result.stocks());
    }
}
