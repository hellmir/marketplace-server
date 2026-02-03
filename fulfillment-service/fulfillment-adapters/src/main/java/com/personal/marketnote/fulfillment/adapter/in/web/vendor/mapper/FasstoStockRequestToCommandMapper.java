package com.personal.marketnote.fulfillment.adapter.in.web.vendor.mapper;

import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoStocksCommand;

public class FasstoStockRequestToCommandMapper {
    public static GetFasstoStocksCommand mapToStocksCommand(
            String customerCode,
            String accessToken,
            String outOfStockYn
    ) {
        return GetFasstoStocksCommand.of(customerCode, accessToken, outOfStockYn);
    }
}
