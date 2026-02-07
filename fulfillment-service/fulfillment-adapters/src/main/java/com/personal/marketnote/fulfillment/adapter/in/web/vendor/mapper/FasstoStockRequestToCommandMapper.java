package com.personal.marketnote.fulfillment.adapter.in.web.vendor.mapper;

import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoStockDetailCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoStocksCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.SyncFasstoAllStockCommand;

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

    public static SyncFasstoAllStockCommand mapToSyncAllCommand(
            String customerCode
    ) {
        return SyncFasstoAllStockCommand.of(customerCode);
    }
}
