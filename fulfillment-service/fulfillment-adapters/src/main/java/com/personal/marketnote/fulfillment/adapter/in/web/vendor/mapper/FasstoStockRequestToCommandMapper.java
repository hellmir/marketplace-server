package com.personal.marketnote.fulfillment.adapter.in.web.vendor.mapper;

import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoStockDetailCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoStocksCommand;

public class FasstoStockRequestToCommandMapper {
    public static GetFasstoStocksCommand mapToStocksCommand(
            String customerCode,
            String accessToken,
            String outOfStockYn
    ) {
        return GetFasstoStocksCommand.of(customerCode, accessToken, outOfStockYn);
    }

    public static GetFasstoStockDetailCommand mapToStockDetailCommand(
            String customerCode,
            String accessToken,
            String cstGodCd,
            String outOfStockYn
    ) {
        return GetFasstoStockDetailCommand.of(customerCode, accessToken, cstGodCd, outOfStockYn);
    }
}
