package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.stock.FasstoStockDetailQuery;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoStocksResult;

public interface GetFasstoStockDetailPort {
    GetFasstoStocksResult getStockDetail(FasstoStockDetailQuery query);
}
