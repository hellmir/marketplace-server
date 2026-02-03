package com.personal.marketnote.fulfillment.port.out.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.stock.FasstoStockQuery;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoStocksResult;

public interface GetFasstoStocksPort {
    GetFasstoStocksResult getStocks(FasstoStockQuery query);
}
