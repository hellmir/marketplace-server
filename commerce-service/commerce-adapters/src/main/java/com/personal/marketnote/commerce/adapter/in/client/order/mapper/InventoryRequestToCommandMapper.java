package com.personal.marketnote.commerce.adapter.in.client.order.mapper;

import com.personal.marketnote.commerce.adapter.in.client.order.request.inventory.RegisterInventoryRequest;
import com.personal.marketnote.commerce.port.in.command.inventory.RegisterInventoryCommand;

public class InventoryRequestToCommandMapper {
    public static RegisterInventoryCommand mapToCommand(
            RegisterInventoryRequest request
    ) {
        return RegisterInventoryCommand.of(request.getPricePolicyId());
    }
}

