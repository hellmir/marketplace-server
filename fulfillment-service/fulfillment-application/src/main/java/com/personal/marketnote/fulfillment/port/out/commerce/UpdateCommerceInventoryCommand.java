package com.personal.marketnote.fulfillment.port.out.commerce;

import com.personal.marketnote.common.utility.FormatValidator;

import java.util.List;

public record UpdateCommerceInventoryCommand(
        List<UpdateCommerceInventoryItemCommand> inventories
) {
    public UpdateCommerceInventoryCommand {
        if (FormatValidator.hasNoValue(inventories)) {
            throw new IllegalArgumentException("Inventory sync items are required for commerce inventory sync.");
        }
    }

    public static UpdateCommerceInventoryCommand of(
            List<UpdateCommerceInventoryItemCommand> inventories
    ) {
        return new UpdateCommerceInventoryCommand(inventories);
    }
}
