package com.personal.marketnote.fulfillment.port.out.commerce;

import com.personal.marketnote.common.utility.FormatValidator;

public record UpdateCommerceInventoryItemCommand(
        Long productId,
        Integer stock
) {
    public UpdateCommerceInventoryItemCommand {
        if (FormatValidator.hasNoValue(productId)) {
            throw new IllegalArgumentException("Product id is required for commerce inventory sync.");
        }
        if (FormatValidator.hasNoValue(stock)) {
            throw new IllegalArgumentException("Stock is required for commerce inventory sync.");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative for commerce inventory sync.");
        }
    }

    public static UpdateCommerceInventoryItemCommand of(
            Long productId,
            Integer stock
    ) {
        return new UpdateCommerceInventoryItemCommand(productId, stock);
    }
}
