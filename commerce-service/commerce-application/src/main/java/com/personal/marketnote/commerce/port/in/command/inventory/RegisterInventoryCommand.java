package com.personal.marketnote.commerce.port.in.command.inventory;

public record RegisterInventoryCommand(
        Long pricePolicyId
) {
    public static RegisterInventoryCommand of(
            Long pricePolicyId
    ) {
        return new RegisterInventoryCommand(pricePolicyId);
    }
}

