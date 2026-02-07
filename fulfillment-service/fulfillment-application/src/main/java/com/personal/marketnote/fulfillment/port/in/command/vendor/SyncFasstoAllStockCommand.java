package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record SyncFasstoAllStockCommand(
        String customerCode,
        String outOfStockYn
) {
    public static SyncFasstoAllStockCommand of(
            String customerCode,
            String outOfStockYn
    ) {
        return new SyncFasstoAllStockCommand(customerCode, outOfStockYn);
    }
}
