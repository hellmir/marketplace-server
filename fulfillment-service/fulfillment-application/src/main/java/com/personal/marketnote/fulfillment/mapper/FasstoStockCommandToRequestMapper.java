package com.personal.marketnote.fulfillment.mapper;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.stock.FasstoStockDetailQuery;
import com.personal.marketnote.fulfillment.domain.vendor.fassto.stock.FasstoStockQuery;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoStockDetailCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoStocksCommand;

public class FasstoStockCommandToRequestMapper {
    public static FasstoStockQuery mapToQuery(GetFasstoStocksCommand command) {
        return FasstoStockQuery.of(
                command.customerCode(),
                command.accessToken(),
                command.outOfStockYn()
        );
    }

    public static FasstoStockDetailQuery mapToDetailQuery(GetFasstoStockDetailCommand command) {
        return FasstoStockDetailQuery.of(
                command.customerCode(),
                command.accessToken(),
                command.cstGodCd(),
                command.outOfStockYn()
        );
    }
}
