package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record SyncFasstoAllStockCommand(
        String customerCode,
        String whCd
) {
    public static SyncFasstoAllStockCommand of(
            String customerCode
    ) {
        return new SyncFasstoAllStockCommand(customerCode, null);
    }

    public static SyncFasstoAllStockCommand of(
            String customerCode,
            String whCd
    ) {
        return new SyncFasstoAllStockCommand(customerCode, whCd);
    }
}
