package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record SyncFasstoAllStockCommand(
        String customerCode
) {
    public static SyncFasstoAllStockCommand of(
            String customerCode
    ) {
        return new SyncFasstoAllStockCommand(customerCode);
    }
}
