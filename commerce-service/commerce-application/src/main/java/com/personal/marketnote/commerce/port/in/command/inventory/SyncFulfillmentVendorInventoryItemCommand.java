package com.personal.marketnote.commerce.port.in.command.inventory;

public record SyncFulfillmentVendorInventoryItemCommand(
        Long productId,
        Integer stock
) {
    public static SyncFulfillmentVendorInventoryItemCommand of(
            Long productId,
            Integer stock
    ) {
        return new SyncFulfillmentVendorInventoryItemCommand(productId, stock);
    }
}
