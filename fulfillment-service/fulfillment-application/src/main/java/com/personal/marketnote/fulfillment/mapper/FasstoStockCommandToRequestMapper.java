package com.personal.marketnote.fulfillment.mapper;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.stock.FasstoStockQuery;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoStocksCommand;

public class FasstoStockCommandToRequestMapper {
    public static FasstoStockQuery mapToQuery(GetFasstoStocksCommand command) {
        return FasstoStockQuery.of(
                command.customerCode(),
                command.accessToken(),
                command.outOfStockYn()
        );
    }
}
