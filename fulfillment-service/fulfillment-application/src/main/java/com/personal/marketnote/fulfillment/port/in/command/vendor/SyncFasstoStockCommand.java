package com.personal.marketnote.fulfillment.port.in.command.vendor;

import java.util.List;

public record SyncFasstoStockCommand(
        String customerCode,
        List<Long> productIds
) {
    public static SyncFasstoStockCommand of(
            String customerCode,
            List<Long> productIds
    ) {
        return new SyncFasstoStockCommand(customerCode, productIds);
    }
}
